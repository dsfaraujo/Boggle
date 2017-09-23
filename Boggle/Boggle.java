package com.example.eleves.tp2_qualite;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static com.example.eleves.tp2_qualite.Jeu.motLong;


/**
 * La classe Boggle encapsule le déroulement d'une partie de Boggle.
 *
 * @author rebecca
 * @author Patricia Shimizu
 * @author Diana Soares
 */
public class Boggle
{
    // Paramètres de la grille de jeu et des dés
    public static final int NB_DES = 16;
    public static final int NB_COLONNES = 4;
    public static final int NB_RANGEES = 4;
    public static final int NB_FACES = 6;

    public static final char TETE = '@';
    public static final char UTILISE = '#';
    static int points;

    // Faces des dés de Boggle internationnal
    public static final char[][] DES =
            {{'E', 'T', 'U', 'K', 'N', 'O'},  // 0
                    {'E', 'V', 'G', 'T', 'I', 'N'},  // 1
                    {'D', 'E', 'C', 'A', 'M', 'P'},  // 2
                    {'I', 'E', 'L', 'R', 'U', 'W'},  // 3
                    {'E', 'H', 'I', 'F', 'S', 'E'},  // 4
                    {'R', 'E', 'C', 'A', 'L', 'S'},  // 5
                    {'E', 'N', 'T', 'D', 'O', 'S'},  // 6
                    {'O', 'F', 'X', 'R', 'I', 'A'},  // 7
                    {'N', 'A', 'V', 'E', 'D', 'Z'},  // 8
                    {'E', 'I', 'O', 'A', 'T', 'A'},  // 9
                    {'G', 'L', 'E', 'N', 'Y', 'U'},  // 10
                    {'B', 'M', 'A', 'Q', 'J', 'O'},  // 11
                    {'T', 'L', 'I', 'B', 'R', 'A'},  // 12
                    {'S', 'P', 'U', 'L', 'T', 'E'},  // 13
                    {'A', 'I', 'M', 'S', 'O', 'R'},  // 14
                    {'E', 'N', 'H', 'R', 'I', 'S'}}; // 15

    // Dictionnaire des mots valides.
    public static final String DICTIONNAIRE = "liste.de.mots.francais.frgut.txt";
    public static final String ENCODAGE_DICTIONNAIRE = "ISO-8859-15";

    /**
     * Place les dés au hasard dans la grille de Boggle et affiche une grille
     * Génére la liste de mots du Joueur 1
     * Génére la liste de mots du Joueur 2
     * Affiche les points du Joueur 1
     * Affiche les points du Joueur 2
     * Affiche le gagnant
     * Affiche le Menu
     *
     * @param args
     */
    public static void main (String args[])
    {
        // Commence une nouvelle manche avec le joueur 1
        char[][] grille = commencerNouvelleManche();
        imprimerGrille(grille);

        // Generer la Liste des mots du Joueur 1
        ArrayList<String> listeJ1 = genererListeJ1();
        ArrayList<String> listeJ1sansDoublons = genererListeSansDoublons(listeJ1);

        // Afficher la grille pour le joueur 2
        System.out.println("Bienvenue au jeu de Boggle Joueur 2\n");
        System.out.println("Voici la grille de jeu :\n");
        imprimerGrille(grille);

        // Generer la Liste des mots du Joueur 2
        ArrayList<String> listeJ2 = genererListeJ2();
        ArrayList<String> listeJ2sansDoublons = genererListeSansDoublons(listeJ2);

        // Afficher le menu Pointage
        int pointsJ1 = compterPointTotal(listeJ1sansDoublons, grille);
        int pointsJ2 = compterPointTotal(listeJ2sansDoublons, grille);
        afficherMenuPointage(pointsJ1, pointsJ2);


        /**
         * Affiche le MENU:
         * 		A. La liste des mots en communs
         * 		B. La liste des mots du joueur 1
         * 		C. La liste des mots du joueur 2
         * 		D. La liste des mots dans la grille
         * 		E. Option de Recommencer
         * 		F. Option de Quitter
         *
         */
        String optionMenu = "Debut";

        while(!optionMenu.toUpperCase().equals("Q"))
        {
            System.out.println("\nVeuillez choisir l'option desirée:");
            System.out.println("	0 - Pour afficher la liste des mots en communs");
            System.out.println("	1 - Pour afficher la liste des mots du joueur 1");
            System.out.println("	2 - Pour afficher la liste des mots du joueur 2");
            System.out.println("	3 - Pour afficher la liste des mots dans la grille");
            System.out.println("	R - Pour commencer une nouvelle manche");
            System.out.println("	Q - Pour quitter");

            Scanner scanIn = new Scanner(System.in);
            optionMenu = scanIn.nextLine();;
            switch (optionMenu.toUpperCase()) {
                case "0": // Afficher la Liste des mots en communs
                    ArrayList<String> listeMotsEgales = listerMotsEgales(listeJ1, listeJ2);
                    listeMotsEgales = genererListeSansDoublons(listeMotsEgales);
                    System.out.println("\nVoici les mots en communs entre les deux joueurs: " + listeMotsEgales);
                    System.out.println("Points: " + compterPointTotal(listeMotsEgales, grille));
                    break;
                case "1": // Afficher la Liste du Joueur 1
                    System.out.println("\nVoici les mots du Joueur 1: " + listeJ1);
                    System.out.println("Points: " + compterPointTotal(listeJ1sansDoublons, grille));
                    break;
                case "2": // Afficher la Liste du Joueur 2
                    System.out.println("\nVoici les mots du Joueur 2: " + listeJ2);
                    System.out.println("Points: " + compterPointTotal(listeJ2sansDoublons, grille));
                    break;
                case "3": // Afficher la liste des mots dans la grille
                    listerMotsGrille(grille);
                    break;
                case "R": // Recommencer une nouvelle manche
                    grille = commencerNouvelleManche();
                    imprimerGrille(grille);
                    listeJ1 = genererListeJ1();
                    listeJ1sansDoublons = genererListeSansDoublons(listeJ1);

                    // Afficher la grille pour le joueur 2
                    System.out.println("Bienvenue au jeu de Boggle Joueur 2\n");
                    System.out.println("Voici la grille de jeu :\n");
                    imprimerGrille(grille);

                    // Generer la Liste des mots du Joueur 2
                    listeJ2 = genererListeJ2();
                    listeJ2sansDoublons = genererListeSansDoublons(listeJ2);

                    // Afficher le menu Pointage
                    pointsJ1 = compterPointTotal(listeJ1sansDoublons, grille);
                    pointsJ2 = compterPointTotal(listeJ2sansDoublons, grille);
                    afficherMenuPointage(pointsJ1, pointsJ2);

                    break;

                case "Q": // Quitter
                    System.out.println("\nSalut!");
                    break;

                default:
                    System.out.println("\nSalut!");
                    break;
            }
        }
    }


    /** Methode afficherMenuPointage
     * Affiche les points du joueur 1
     * Affiche les points du joueur 2
     * Affiche le gagnant
     *
     * @param pointsJ1, pointsJ2
     *
     */
    public static void afficherMenuPointage(int pointsJ1, int pointsJ2)
    {
        System.out.println("\n************************************************************");
        System.out.println("Partie Terminée\n");
        System.out.println("Joueur 1: " + pointsJ1 + " points");
        System.out.println("Joueur 2: " + pointsJ2 + " points");
        System.out.println("************************************************************");
        if(pointsJ1 > pointsJ2)
        {
            System.out.println("\nJoueur 1 a gagné, félicitations!\n");
        }
        else if(pointsJ2 > pointsJ1)
        {
            System.out.println("\nJoueur 2 a gagné, félicitations!\n");
        }
        else
        {
            System.out.println("\nPartie nulle!!\nJoueurs 1 et 2 ont la même quantité de points!\n");
        }
        System.out.println("************************************************************\n");
    }


    /** Methode commencerNouvelleManche
     *  Début du joue:
     * 	- Initialiser la grille au hasard
     *  - Générer la grille
     *
     * @return grille
     */
    public static char[][] commencerNouvelleManche()
    {
        points =0;
        //System.out.println("Bienvenue au jeu de Boggle Joueur 1\n");
        //System.out.println("Voici la grille de jeu :\n");


        // Initialiser la grille au hasard
        char[][] grille = new char[NB_RANGEES][NB_COLONNES];

        genererGrille(grille);

        //Generer grille manuellement
		/*grille[0][0] = 'D';
		grille[0][1] = 'R';
		grille[0][2] = 'D';
		grille[0][3] = 'E';
		grille[1][0] = 'N';
		grille[1][1] = 'S';
		grille[1][2] = 'S';
		grille[1][3] = 'O';
		grille[2][0] = 'W';
		grille[2][1] = 'E';
		grille[2][2] = 'A';
		grille[2][3] = 'L';
		grille[3][0] = 'A';
		grille[3][1] = 'I';
		grille[3][2] = 'I';
		grille[3][3] = 'E';*/

        return grille;
    }



    /** Methode genererListeJ1
     *  Garder les mots du Joueur 1
     *  Ne pas garder les doublons
     *
     * @return listeJ1
     */
    public static ArrayList<String> genererListeJ1()
    {
        int nbMotJ1 = 0;
        ArrayList<String> listeJ1 = new ArrayList<>();
        String motJ1 = "Debut";
        Scanner scanIn = new Scanner(System.in);

        while(!motJ1.equals("0"))
        {
            //System.out.println("\nEntrez le mot " + (nbMotJ1+1) + ": ");
            //motJ1 = scanIn.nextLine();
            if(!motJ1.equals("0"))
            {
                listeJ1.add(motJ1);
            }
            nbMotJ1++;
        }

        // Cacher les mots du Jouer 1
        for(int i = 0; i < 20; i++)
        {
            //System.out.println();
        }
        return listeJ1;
    }



    /** Methode genererListeJ2
     *  Garder les mots du Joueur 2
     *  Ne pas garder les doublons
     *
     * @return listeJ1
     */
    public static ArrayList<String> genererListeJ2()
    {
        int nbMotJ2 = 0;
        ArrayList<String> listeJ2 = new ArrayList<>();
        String motJ2 = "Debut";
        Scanner scanIn = new Scanner(System.in);

        while(!motJ2.equals("0"))
        {
            System.out.println("\nEntrez le mot " + (nbMotJ2+1) + ": ");
            motJ2 = scanIn.nextLine();
            if(!motJ2.equals("0"))
            {
                listeJ2.add(motJ2);
            }
            nbMotJ2++;
        }
        return listeJ2;

    }


    /** Methode genererListeSansDoublons
     *  Recevois la liste du Joueur
     *  Efface les doublons
     *
     * @param listeJoueur
     * @return listeSansDoublons
     */
    public static ArrayList<String> genererListeSansDoublons(ArrayList<String> listeJoueur)
    {
        ArrayList<String> listeSansDoublons = new ArrayList<>();

        if(listeJoueur.size() > 0)
        {
            String motTemp = listeJoueur.get(0);
            listeSansDoublons.add(formatter(motTemp).toLowerCase());

            for(int i = 1; i < listeJoueur.size(); i++)
            {
                motTemp = listeJoueur.get(i);
                if(!listeSansDoublons.contains(formatter(motTemp).toLowerCase()))
                {
                    listeSansDoublons.add(formatter(motTemp).toLowerCase());
                }
            }
        }
        return listeSansDoublons;
    }



    /** Methode listerMotsEgales
     * Faire la comparaison entre les listes du Joueur 1 et Joueur 2
     *
     * @param listeJ1, listeJ2
     * @return listeMotsEgales
     */
    public static ArrayList<String> listerMotsEgales(ArrayList<String> listeJ1, ArrayList<String> listeJ2)
    {
        ArrayList<String> listeMotsEgales = new ArrayList<>();
        int tailleListeJ1 = listeJ1.size();
        int tailleListeJ2 = listeJ2.size();

        for(int i = 0; i < tailleListeJ1; i++)
        {
            for(int j = 0; j < tailleListeJ2; j++)
            {
                if(listeJ1.get(i).contains(listeJ2.get(j)))
                {
                    listeMotsEgales.add(listeJ1.get(i));
                }
            }
        }
        return listeMotsEgales;

    }


    /** Methode compterPointTotal
     *  Valide si le mot est dans le dictionnaire
     *  Si le mot est valide, donne la quantite de point à chaque mot
     *  Les mots qui contient des accentuations mais possèdent la même graphie,
     *  	compte une seul fois pour le pointage
     *  	(ex : « osés », « oses » comptent juste une fois)
     *  Retourne le total de point
     *
     * @param liste du joueur, grille de la manche
     * @return total de points
     */
    public static int compterPointTotal(ArrayList<String> liste, char[][] grille)
    {
        int totalPoint = 0;
        int tailleListe = liste.size();
        String mot = "debut";

        for(int i = 0; i < tailleListe; i++)
        {
            mot = liste.get(i);
            mot = formatter(mot);
            if(contientMot(mot, grille))
            {
                boolean result = validerMotDictionnaire(mot.toLowerCase());

                if(result)
                {
                    if(mot.length() >= 3 && mot.length() <= 4)
                    {
                        totalPoint += 1;
                    }
                    else if(mot.length() == 5)
                    {
                        totalPoint += 2;
                    }
                    else if(mot.length() == 6)
                    {
                        totalPoint += 3;
                    }
                    else if(mot.length() == 7)
                    {
                        totalPoint += 5;
                    }
                    else if(mot.length() > 7)
                    {
                        totalPoint += 11;

                    }
                    else
                    {
                        totalPoint += 0;
                    }
                    motsLesPlusLongs(mot);
                }
            }
        }
        return totalPoint;
    }


    /** Methode compterJeuTP2
     *  Valide si le mot est dans le dictionnaire
     *  Si le mot est valide, donne la quantite de point à chaque mot
     *  Les mots qui contient des accentuations mais possèdent la même graphie,
     *  	compte une seul fois pour le pointage
     *  	(ex : « osés », « oses » comptent juste une fois)
     *  Retourne le total de point
     *
     * @param mot rentres par le joueur, grille de la manche
     * @return total de points
     */
    public static int pointsJeuTP2(String mot, char[][] grille){
        int totalPoint = 0;
        //int tailleListe = liste.size();

        ArrayList<String> liste = new ArrayList<>();
        String motDico;

        for (int i=0; i<Jeu.dico.size(); i++){
            motDico = Jeu.dico.get(i);
            motDico = formatter(motDico);
            if(mot.equals(motDico)){
                if(mot.length() >= 3 && mot.length() <= 4)
                {
                    totalPoint += 1;
                }
                else if(mot.length() == 5)
                {
                    totalPoint += 2;
                }
                else if(mot.length() == 6)
                {
                    totalPoint += 3;
                }
                else if(mot.length() == 7)
                {
                    totalPoint += 5;
                }
                else if(mot.length() > 7)
                {
                    totalPoint += 11;
                }
                else
                {
                    totalPoint += 0;
                }
               // motsLesPlusLongs (mot);
            }

        }
        points = points+ totalPoint;

        return points;
    }



    /** Method validerMotDictionnaire
     * Cherche si le mot est dans le Dictionnaire
     *
     * @param mot à être validé
     * @return true si vrai, false si non
     */
    public static boolean validerMotDictionnaire(String mot)
    {
        boolean result = false;
        Scanner in = null;
        try{
            in = new Scanner(new File(DICTIONNAIRE), ENCODAGE_DICTIONNAIRE);


            while(in.hasNextLine() && !result)
            {

                String motDic = in.nextLine();
                motDic = formatter(motDic);
                if(mot.equals(motDic))
                {
                    result = true;
                }
                else
                {
                    result = false;
                }
            }
            in.close();
        }
        catch (IOException e)
        {
            System.out.println("Le dictionnaire n'existe pas.");
        }
        finally
        {
            if (in != null)
            {
                in.close();
            }
        }
        return result;
    }


    /** Methode listerMotsGrille
     * Lister tous les mots dans la grille avec le chemin
     *
     * @param grille de la manche actuelle
     */
    public static void listerMotsGrille(char[][] grille)
    {
        Scanner in = null;
        try{
            in = new Scanner(new File(DICTIONNAIRE), ENCODAGE_DICTIONNAIRE);

            while(in.hasNextLine())
            {
                String mot = in.nextLine();
                String motTemp = formatter(mot);

                int[][] chemin = new int[mot.length()][2];

                if (contientMot(motTemp, grille, chemin))
                {
                    System.out.println("\n" + mot+ ": ");
                    imprimerChemin(grille,chemin);
                }
            }
            in.close();
        }
        catch (IOException e)
        {
            System.out.println("Le dictionnaire n'existe pas.");
        }
        finally
        {
            if (in != null)
            {
                in.close();
            }
        }
    }


    /**
     * Générer la grille en plaçant les dés de manière aléatoire puis en tirant
     * une face au hasard pour chaque dé.
     *
     * @param grille
     */
    public static void genererGrille(char [][] grille)
    {
        boolean [] utilise = new boolean[NB_DES];

        // Piger puis jeter chaque dé.
        for (int i = 0; i < NB_RANGEES; i++)
        {
            for (int j = 0; j < NB_COLONNES; j++)
            {
                int d = 0;
                do
                {
                    d = (int)(Math.random() * NB_DES);
                } while (utilise[d]);
                utilise[d] = true;
                grille[i][j] = DES[d][(int)(Math.random() * NB_FACES)];
            }
        }
    }

    /**
     * Enlever tous les accents, espaces et tirets du mot s.
     *
     * @param s mot dont on doit retirer les accents et les tirets
     * @return s sans les accents et les tirets
     */
    public static String formatter(String s)
    {
        return s.toUpperCase().
                replace("É", "E").replace("È", "E").replace("Ê", "E").replace("Ë", "E").
                replace("Â", "A").replace("À", "A").replace("Ä", "A").
                replace("Î", "I").replace("Ï", "I").
                replace("Ô", "O").replace("Ö", "O").
                replace("Û", "U").replace("Ù", "U").replace("Ü", "U").
                replace("Ç", "C").
                replace("-",  "").
                replace(" ", "");
    }


    /**
     * Imprime la grille g à la console
     *
     * @param g grille à imprimer
     */
    public static void imprimerGrille(char [][] g)
    {
        for (int i = 0; i < NB_RANGEES; i++)
        {
            System.out.print(" ");
            for (int j = 0; j < NB_COLONNES; j++)
            {
                System.out.print(g[i][j] + "  ");
            }
            System.out.println("");
        }
    }

    /**
     * Imprimer la grille avec numérotation du chemin dans celle-ci.
     *
     * @param g grille à imprimer
     * @param chemin cases à souligner dans la grille
     */
    public static void imprimerChemin(char [][] g, int[][] chemin)
    {
        boolean dansLeChemin = false;
        for (int i = 0; i < NB_RANGEES; i++)
        {
            System.out.print(" ");
            for (int j = 0; j < NB_COLONNES; j++)
            {
                dansLeChemin = false;
                for (int k = 0; k < chemin.length; k++)
                {
                    if (chemin[k][0] == i && chemin[k][1] == j)
                    {
                        System.out.print(g[i][j]+ "("+k+")   ");
                        dansLeChemin = true;
                    }
                }

                if (!dansLeChemin)
                    System.out.print(g[i][j]+"      ");
            }
            System.out.println("");
        }
    }

    /**
     * Chercher le mot dans la grille.
     *
     * @param mot mot à rechercher
     * @param grille grille dans laquelle le chercher
     * @return vrai si le mot est contenu dans la grille et faux sinon.
     */
    public static boolean contientMot(String mot, char[][] grille)
    {
        int[][]chemin = new int[mot.length()][2];
        return contientMot(mot, grille, chemin);
    }


    /**
     * Chercher le mot dans la grille. Si grille contient le mot,
     * mettre dans le tableau chemin les coordonnées dans la grille des lettres
     * du mot.
     *
     * @param mot mot à rechercher
     * @param grille grille dans laquelle le chercher
     * @param chemin contiendra les coordonnées du mot dans la grille
     * @return vrai si le mot est contenu dans la grille et faux sinon.
     */
    public static boolean contientMot(String mot, char[][] grille, int[][] chemin)
    {
        for (int i = 0; i < NB_RANGEES; i++)
        {
            for (int j = 0; j < NB_COLONNES; j++)
            {
                if (contientMot(mot, grille, i, j, chemin, 0))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Recherche le mot dans la grille à partir de la case à la rangée r et la
     * colonne c et met dans le tableau chemin (à partir de l'indice ind) les
     * coordonnées du mot dans la grille.
     *
     * @param mot mot à rechercher
     * @param grille grille dans laquelle chercher le mot
     * @param r numéro de rangée
     * @param c numéro de colonne
     * @param chemin contiendra les coordonnées du mot dans la grille
     * @param ind position dans le tableau chemin
     * @return vrai si on peut trouver le mot à partir de la case (r,c) de grille et faux sinon.
     */
    public static boolean contientMot(String mot, char[][] grille, int r, int c, int[][] chemin, int ind)
    {
        char premier = grille[r][c];

        // Toutes les grilles comportent le mot vide
        if (mot.length() == 0)
        {
            return true;
        }
        else if (premier == mot.charAt(0))
        {
            chemin[ind][0] = r;
            chemin[ind][1] = c;

            // On ne peut utiliser un dé qu'une fois.
            grille[r][c] = UTILISE;

            // On cherche le prochain caractère dans le voisinage (diagonales inclues)
            for (int i = Math.max(0, r - 1); i <= Math.min(r + 1, NB_RANGEES - 1); i++)
            {
                for (int j = Math.max(0, c - 1); j <= Math.min(c + 1, NB_COLONNES - 1); j++)
                {
                    if (contientMot(mot.substring(1, mot.length()), grille, i, j, chemin, ind + 1))
                    {
                        grille[r][c] = premier;
                        return true;
                    }
                }
            }
        }
        grille[r][c] = premier;
        return false;
    }

    /*
         * Cette methode prendre toutes les mots plus grandes dans la grille et les affiche a la consele
         * Input: ArrayList qui contient les mots
         * 		 grille du jeu
         * Output: Le ArrayList avec des mots plus grandes qui contient dans la grille
         */
    static String motsLesPlusLongs (String motEnCours){
        String mMot = new String();
        ArrayList<String> mots = new ArrayList<String>();
        String s1 = new String();
        int sizeMax = 0;
        mots.add(motEnCours);

        for(int i = 0; i< mots.size(); i++){
            s1 = mots.get(i);
            if(i==0){
                sizeMax = s1.length();
            }

            if(sizeMax < s1.length()){
                sizeMax = s1.length();
            }

        }
        for(int j = 0; j< mots.size(); j++){
            s1 = mots.get(j);

            if(s1.length() == sizeMax){
                motLong.add(s1);
            }
            mMot = motLong.get(j);
        }
        return mMot;
    }



}

