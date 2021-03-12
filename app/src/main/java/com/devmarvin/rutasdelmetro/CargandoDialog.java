package com.devmarvin.rutasdelmetro;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;

public class CargandoDialog {
    private Activity mActivity;
    private AlertDialog mAlertDialog;
    CargandoDialog(Activity activity){
        mActivity = activity;
    }
    void iniciarCargandoDialog(int partida, int destino) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        LayoutInflater inflater = mActivity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_dialog,null));
        builder.setCancelable(false);
        mAlertDialog = builder.create();
        mAlertDialog.show();
        Intent intent = ActivityRutaFinal.newIntent(mActivity,new int[] {partida,destino});
        mActivity.startActivity(intent);
    }
    void cancelarDialog(){
        mAlertDialog.cancel();
    }
}
