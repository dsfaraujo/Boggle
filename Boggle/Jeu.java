package com.example.eleves.tp2_qualite;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * La classe Jeu encapsule le déroulement d'une interface de jeu de Boggle.
 *
 * @author rebecca
 * @author Patricia Shimizu
 * @author Diana Soares
 */

public class Jeu extends AppCompatActivity {

    public static ArrayList<String> dico;
    public static ArrayList<String> motLong;
    char[][] grille;       // Ceci devrait être encapsulé dans une classe à part
    // Ceci devrait être encapsulé dans une classe à part


    String motEnCours = "";
    int[][] chemin = new int[Boggle.NB_COLONNES * Boggle.NB_RANGEES][2];
    int[][] cheminTemp = new int[Boggle.NB_COLONNES * Boggle.NB_RANGEES][2];

    /**
     * Création de l'interface, chargement du dictionnaire
     * Initialisation de la grille et démarrage de la première
     * manche
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Scanner in = null;
        try {
            dico = new ArrayList<String>();
            in = new Scanner(getAssets().open("liste.de.mots.francais.frgut.txt"), "ISO-8859-15");
            // ce fichier doit être dans /src/main/assets

            while (in.hasNextLine()) {
                dico.add(in.nextLine());
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Le dico n'existe pas.");
        } finally {
            if (in != null) {
                in.close();
            }
        }
        setContentView(R.layout.activity_jeu);

        // Initialiser la grille
        grille = Boggle.commencerNouvelleManche();
        Scanner scanIn = new Scanner(System.in);
        String mots = "test";
    /*    grille =  new char[][]
                {
                        {'A','B','C','D'},
                        {'E','F','G','H'},
                        {'I','J','K','L'},
                        {'M','N','O','P'}
                };*/
        effacerChemin();
        afficherGrille();
        demarrerChrono();
        //mots = scanIn.nextLine();

        final Button reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (Boggle.contientMot(motEnCours, grille)) {
                    //Boggle.motsLesPlusLongs(motEnCours);
                    Toast.makeText(getApplicationContext(), motEnCours, Toast.LENGTH_SHORT).show();
                    TextView pointage = (TextView) findViewById(R.id.pointage);
                    pointage.setText("Votre pointage : " + Boggle.pointsJeuTP2(motEnCours, grille)); // Mettez à jour le pointage.
                } else {
                    Toast.makeText(getApplicationContext(), motEnCours, Toast.LENGTH_SHORT).show();
                    TextView pointage = (TextView) findViewById(R.id.pointage);
                    pointage.setText("Votre pointage : " + Boggle.pointsJeuTP2(motEnCours, grille)); // Mettez à jour le pointage.
                }

                effacerChemin();
                afficherGrille();

            }
        });
    }

    /**
     * Démarrage du chronomètre.
     * Le chronomètre recommence après 30 secondes.
     */
    public void demarrerChrono() {
        final TextView chrono = (TextView) findViewById(R.id.chrono);
        CountDownTimer cT = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                String v = String.format("%02d", millisUntilFinished / 60000);
                int va = (int) ((millisUntilFinished % 60000) / 1000);
                chrono.setText(v + ":" + String.format("%02d", va));
            }

            public void onFinish() {
                chrono.setText("fini!");
                nextManche();
            }
        };
        cT.start();
    }

    /**
     * Redémare une manche si l'usager souhaite rejouer.
     */
    public void nextManche() {
        new AlertDialog.Builder(this)
                .setTitle("Manche terminée").setMessage("Vous avez obtenu " + Boggle.pointsJeuTP2(motEnCours, grille)
                + " points. Voulez-vous recommencer?\n")
                .setNegativeButton("OUI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        // Imprimer une nouvelle grille;
                        effacerChemin();
                        grille = Boggle.commencerNouvelleManche();
                        TextView pointage = (TextView) findViewById(R.id.pointage);
                        pointage.setText("Votre pointage : 0"); // Mettez à jour le pointage.
                        afficherGrille();
                        demarrerChrono();
                    }
                })
                .setPositiveButton("NON", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                })
                .show();
    }

    /**
     * Détermine si la case [rangee][colonne] a déjà été surlignée
     *
     * @param rangee  indice de la rangée
     * @param colonne indice de la colonne
     * @return Vrai si la case [rangee][colonne] est déjà surlignée
     * Faux sinon.
     */
    public boolean dansChemin(int rangee, int colonne) {
        for (int i = 0; i < motEnCours.length(); i++)
        {
            if ((chemin[i][0] == rangee) && (chemin[i][1] == colonne))
                return true;
        }
        return false;
    }

    /**
     * Détermine si la case [rangee][colonne] peut être surlignée.
     * Une case peut être surlignée si le mot commence ou si la case
     * est voisine de la dernière case surlignée.
     *
     * @param rangee  indice de la rangée
     * @param colonne indice de la colonne
     * @return Vrai si la case peut être surlignée et Faux sinon.
     */
    public boolean voisin(int rangee, int colonne) {
        boolean estVoisin = true;

        int lettreAnterieur = motEnCours.length() - 1;

        if(motEnCours.length() == 0)
        {
            estVoisin = true;
        }
        else
        {
            if(chemin[lettreAnterieur][0] == rangee || chemin[lettreAnterieur][0] == rangee-1 || chemin[lettreAnterieur][0] == rangee+1)
            {
                if(chemin[lettreAnterieur][1] == colonne || chemin[lettreAnterieur][1] == colonne-1 || chemin[lettreAnterieur][1] == colonne+1)
                {
                    estVoisin = true;
                }
                else
                {
                    estVoisin = false;
                }
            }
            else
            {
                estVoisin = false;
            }
        }
        return estVoisin;


    }


    /**
     * Efface le chemin et le mot en cours.
     */
    public void effacerChemin()
    {
        for (int i = 0; i < Boggle.NB_COLONNES*Boggle.NB_RANGEES; i++) {
            chemin[i][0] = -1;
            chemin[i][1] = -1;
        }
        motEnCours = "";
    }

    /**
     * Affiche la grille dans le TableLayout
     * Donne a chaque case dans la grille une fonction qui permet
     * de la surligner.
     */
    public void afficherGrille()
    {
        TableLayout stk = (TableLayout) findViewById(R.id.grille);

        stk.removeAllViews();

        for (int i = 0; i < Boggle.NB_RANGEES; i++) {
            TableRow tbrow = new TableRow(this);
            for (int j = 0; j < Boggle.NB_COLONNES; j++)
            {

                final TextView de = new TextView(this);
                final int rangee = i;
                final int colonne = j;
                String s= ""+grille[i][j];
                de.setText(s);
                de.setTextSize(50);
                de.setIncludeFontPadding(false);
                de.setGravity(Gravity.CENTER);
                de.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {



                        if (!dansChemin(rangee, colonne) && voisin(rangee, colonne)) {
                            chemin[motEnCours.length()][0] = rangee;
                            chemin[motEnCours.length()][1] = colonne;
                            motEnCours += de.getText().toString();
                            if (((rangee + colonne) % 2) == 0) {
                                de.setTextColor(Color.MAGENTA);
                                de.setBackgroundColor(Color.WHITE);
                            } else {
                                de.setTextColor(Color.WHITE);
                                de.setBackgroundColor(Color.MAGENTA);

                            }
                        }
                        for (int i = 0; i <= 15; i++) {


                            System.out.println(chemin[i][1]);
                            System.out.println(chemin[i][0]);
                        }
                    }
                });

                if (((i+j) % 2) ==0) {
                    de.setTextColor(Color.BLACK);
                    de.setBackgroundColor(Color.LTGRAY);
                }
                else {
                    de.setTextColor(Color.WHITE);
                    de.setBackgroundColor(Color.DKGRAY);

                }
                tbrow.addView(de);
            }
            stk.addView(tbrow);
        }
    }
}
