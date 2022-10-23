package com.example.films;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

public class UsuarioManager {

    private static UsuarioManager instancia;
    Dao<Usuario, Integer> dao;

    public UsuarioManager() {
    }

    public UsuarioManager(Context context) {
        OrmLiteSqliteOpenHelper helper = OpenHelperManager.getHelper(context, DBHelper.class);
        try {
            dao = helper.getDao(Usuario.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static UsuarioManager getInstancia(Context context) {
        if(instancia == null) {
            instancia = new UsuarioManager(context);
        }
        return instancia;
    }

    public Dao<Usuario, Integer> getDao() {
        return dao;
    }

    public void setDao(Dao<Usuario, Integer> dao) {
        this.dao = dao;
    }

    public List<Usuario> getUsuarios() throws Exception{
        return dao.queryForAll();
    }

    public void agregarUsuario(Usuario usuario) throws Exception {
        dao.create(usuario);
    }

    public Usuario traerUsuario(String usuario) throws Exception {
        Usuario user = null;
        try{
            List<Usuario> usuarios = getUsuarios();
            Log.i("cantidad", String.valueOf(usuarios.size()));
            boolean encontrado = false;
            int i = 0, cantidad = usuarios.size();
            while ( (i<cantidad) && (!encontrado) ) {
                if (usuarios.get(i).getUsuario().equals(usuario)) {
                    user = usuarios.get(i);
                    encontrado = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public long existeUsuario(String nombreUsuario) throws Exception {
        String nombreUser = nombreUsuario.toLowerCase(Locale.ROOT);
        long cantidad = dao.queryRawValue("select count(*) from usuarios where lower(usuario) = '"+nombreUser+"'");
        return cantidad;
    }

    public Usuario traer(List<Usuario> usuarios, String nombre) {
        Usuario usuario = null;
        boolean encontrado = false;
        int i = 0, cantidad = usuarios.size();
        while ( (i<cantidad) && (!encontrado) ) {
            if (usuarios.get(i).getUsuario().equals(nombre)) {
                usuario = usuarios.get(i);
                encontrado = true;
            }
            i++;
        }
        return usuario;
    }
}
