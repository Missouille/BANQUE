package banque;

import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class bddConnect
{
	String url = "jdbc:mysql://localhost:3306/banque?user=root&password=''";
    String utilisateur = "root";
    String motDePasse = "";
	Connection connexion = null;
	Statement statement = null;
	ResultSet resultat = null;
	
	/**
	 * Constructeur par d�faut
	 * Il se charge de la connexion � la base de donn�e
	 */
	public bddConnect()
	{
		try
		{
		    /* 
		     * Ouverture de la connexion, initialisation d'un Statement, initialisation d'un ResultSet, etc.
		     */
			connexion = DriverManager.getConnection( url, utilisateur, motDePasse);
			System.out.println("Driver OK");
			/* Cr�ation de l'objet g�rant les requ�tes */
	        statement = connexion.createStatement();
	        System.out.println("Connexion OK");
	        

		}
		catch ( SQLException e )
		{
		    /* Traiter les erreurs �ventuelles ici. */
			//System.out.println("TUTUUUU");
			System.out.println("Connection problem " + e.getMessage());
		}
	}
	/**
	 * Proc�dure d'insertion en base d'un compte
	 * @param num
	 * 		: Envoi du num�ro de compte
	 * @param nom
	 * 		: Envoi du nom du titulaire du compte
	 * @param solde
	 * 		: Le solde initial � l'ouverture du compte
	 * @param type
	 * 		: Ce param�tre va nous permettre de connaitre le type de compte (classique, �pargne ou courant)
	 */
	public void insertionCompte(int num, String nom, double solde, int type)
	{
		/* Ex�cution d'une requ�te d'�criture */
        try {
        	//System.out.println("num�ro : " + num + " nom : " + nom + " solde : " + solde);
			int statut = statement.executeUpdate( "INSERT INTO compte (NUMERO, NOMTITULAIRE, SOLDE, TYPE) VALUES (" + num + ", " + "'" + nom + "'" + " ," + solde + ", " + type + ");" );
		} catch (SQLException e) {
			// cr�ation de la frame
			JFrame frame = null;
			//fenetre d'erreur
			JOptionPane.showMessageDialog(frame,
			    "Compte existant",
			    "Erreur de cr�ation",
			    JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * Proc�dure d'insertion en base d'un compte �pargne
	 * @param taux
	 * 		: taux de r�mun�ration du compte
	 * @see insertionCompte
	 */
	public void insertionCompteEpargne(int num, String nom, double solde, int type, double taux)
	{
		/* Ex�cution d'une requ�te d'�criture */
        try {
        	//System.out.println("num�ro : " + num + " nom : " + nom + " solde : " + solde + "taux : " + taux);
			int statut = statement.executeUpdate( "INSERT INTO compte_epargne (NUMERO, NOMTITULAIRE, SOLDE, TYPE, TAUX) VALUES (" + num + ", " + "'" + nom + "'" + " ," + solde + "," + type + "," + taux +");" );
		} catch (SQLException e) {
			// cr�ation de la frame
			JFrame frame = null;
			//fenetre d'erreur
			JOptionPane.showMessageDialog(frame,
			    "Compte existant",
			    "Erreur de cr�ation",
			    JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * Proc�dure d'instertion en base d'un compte courant
	 * @param decouvertAuto
	 * 		: Param�tre permettant de connaitre le d�couvert auquel a le droit le client
	 * @see insertionCompte
	 */
	public void insertionCompteCourant(int num, String nom, double solde, int type, double decouvertAuto)
	{
		/* Ex�cution d'une requ�te d'�criture */
        try {
        	//System.out.println("num�ro : " + num + " nom : " + nom + " solde : " + solde + "d�couvert autoris� : " + decouvertAuto);
			int statut = statement.executeUpdate( "INSERT INTO compte_courant (NUMERO, NOMTITULAIRE, SOLDE, TYPE, DECOUVERTAUTORISE) VALUES (" + num + ", " + "'" + nom + "'" + " ," + solde + "," + type + "," + decouvertAuto + ");" );
		} catch (SQLException e) {
			// cr�ation de la frame
			JFrame frame = null;
			//fenetre d'erreur
			JOptionPane.showMessageDialog(frame,
			    "Compte existant",
			    "Erreur de cr�ation",
			    JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Proc�dure de mise � jour en base du taux de r�mun�ration d'un compte �pargne
	 * @param taux
	 * 		: re�oit en param�tre le nouveau taux d�finit par le banquier
	 * @see insertionCompteEpargne
	 */
	public void mise_a_jour_taux(double taux)
	{
		/* Ex�cution d'une requ�te d'�criture */
        try {
        	//System.out.println("Nouveau taux : " + taux);
			int statut = statement.executeUpdate( "UPDATE compte_epargne SET TAUX = " + taux + ";" );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("PROBLEME UPDATE TAUX " + e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 * Proc�dure de mise � jour d'un d�couvert autoris� en passant en param�tre
	 * @param num
	 * 		: le num�ro de compte du client
	 * @param decouv
	 * 		: le nouveau montant du d�couvert autoris�
	 * @see insertionCompteCourant
	 */
	public void mise_a_jour_decouvert(int num, double decouv)
	{
		/* Ex�cution d'une requ�te d'�criture */
        try {
        	//System.out.println("Nouveau decouvert : " + decouv);
			int statut = statement.executeUpdate( "UPDATE compte_courant SET DECOUVERTAUTORISE = " + decouv + " WHERE NUMERO = " + num + ";" );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("PROBLEME UPDATE DECOUVERT " + e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 * Requ�te affichant le solde du compte
	 * @param num
	 * @return Le solde du client
	 * 
	 */
	public double consulte(int num)
	{
		 double soldeCom = 0;
		/* Ex�cution d'une requ�te de lecture */
		try
		{
			ResultSet resultat = statement.executeQuery( "SELECT NUMERO, SOLDE, NOMTITULAIRE  FROM compte WHERE " + num +" = NUMERO;" );

			/* R�cup�ration des donn�es du r�sultat de la requ�te de lecture */
			while ( resultat.next() )
			{
			    int numeroCom = resultat.getInt( "NUMERO" );
			    soldeCom = resultat.getDouble("SOLDE");
			    String nomTit = resultat.getString("NOMTITULAIRE");

			}
		}
			catch (SQLException e)
			{
			// TODO Auto-generated catch block
			System.out.println("PROBLEME SELECT " + e.getMessage());
			e.printStackTrace();
		}
		return soldeCom;
	}
	/**
	 * Requ�te permettant de d�poser une somme entr�e par le client.
	 * @param num
	 * 		: Envoi du num�ro de compte
	 * @param somme
	 * 		: Envoi de la somme que veut d�poser le client
	 */
	public void deposer(int num, double somme)
	{
		/* Ex�cution d'une requ�te d'�criture */
        try {
        	//System.out.println("Nouveau solde : " + somme);
			int statut = statement.executeUpdate("UPDATE compte SET SOLDE = SOLDE + " + somme + " WHERE NUMERO = " + num + ";" );
			int statutBis = statement.executeUpdate("INSERT INTO consultations (NUMERO_COMPTE, TYPEOPERATION, MONTANT, DATE) VALUES (" + num + ", 'D�p�t', " + somme + ", NOW());" );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("PROBLEME UPDATE DEPOT " + e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 * Requ�te permettant d'effectuer un retrait sur le compte du client
	 * @param num
	 * 		: Envoi du num�ro de compte
	 * @param somme
	 * 		: Envoi de la somme � retirer
	 */
	public void retirer(int num, double somme)
	{
		/* Ex�cution d'une requ�te d'�criture */
        try {
        	//System.out.println("Nouveau solde : " + somme);
			int statut = statement.executeUpdate("UPDATE compte SET SOLDE = SOLDE - " + somme + " WHERE NUMERO = " + num + ";" );
			int statutBis = statement.executeUpdate("INSERT INTO consultations (NUMERO_COMPTE, TYPEOPERATION, MONTANT, DATE) VALUES (" + num + ", 'Retrait', " + somme + ", NOW());" );
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("PROBLEME UPDATE RETRAIT " + e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 * Requ�te de recherche de type de compte
	 * @param num
	 * 		: Envoi du num�ro de compte qui permet la recherche du type de compte (�pargne, courant, classique)
	 * @return Le type de compte 1 pour classique 2 pour �pargne et 3 pour un compte courant
	 */
	public int recupType(int num)
	{
		int type = 0;
		/* Ex�cution d'une requ�te de lecture */
		try
		{
			ResultSet resultat = statement.executeQuery( "SELECT TYPE  FROM compte WHERE " + num +" = NUMERO;" );

			/* R�cup�ration des donn�es du r�sultat de la requ�te de lecture */
			while ( resultat.next() )
			{
			    type = resultat.getInt( "TYPE" );
			    /* Traiter ici les valeurs r�cup�r�es. */
			}
		}
			catch (SQLException e)
			{
			// TODO Auto-generated catch block
			System.out.println("PROBLEME SELECT " + e.getMessage());
			e.printStackTrace();
		}
		return type;
	}
	/**
	 * Requ�te permettant de r�cup�r� le taux de r�mun�ration des comptes
	 * @param num
	 * 		: Envoi du num�ro de compte afin de pouvoir r�cup�r� le taux de r��un�ration
	 * @return Le taux de r�mun�ration du compte
	 */
	public double recupTaux(int num)
	{
		double taux = 0;
		/* Ex�cution d'une requ�te de lecture */
		try
		{
			ResultSet resultat = statement.executeQuery( "SELECT TAUX  FROM compte_epargne;" );

			/* R�cup�ration des donn�es du r�sultat de la requ�te de lecture */
			while ( resultat.next() )
			{
			    taux = resultat.getDouble( "TAUX" );
			    /* Traiter ici les valeurs r�cup�r�es. */
			}
		}
			catch (SQLException e)
			{
			// TODO Auto-generated catch block
			System.out.println("PROBLEME SELECT " + e.getMessage());
			e.printStackTrace();
		}
		return taux;
	}
	/**
	 * Requ�te permettant de r�cup�r� le d�couvert pour un compte courant
	 * @param num
	 * 		: Envoi du num�ro de compte afin de r�cup�rer le d�couvert
	 * @return Le d�couvert autoris� pour le compte en cours de consultation
	 */
	public double recupDecouvert(int num)
	{
		double decouvert = 0;
		/* Ex�cution d'une requ�te de lecture */
		try
		{
			ResultSet resultat = statement.executeQuery( "SELECT DECOUVERTAUTORISE  FROM compte_courant WHERE NUMERO = " + num + ";" );

			/* R�cup�ration des donn�es du r�sultat de la requ�te de lecture */
			while ( resultat.next() )
			{
			    decouvert = resultat.getDouble( "DECOUVERTAUTORISE" );
			    /* Traiter ici les valeurs r�cup�r�es. */
			}
		}
			catch (SQLException e)
			{
			// TODO Auto-generated catch block
			System.out.println("PROBLEME SELECT " + e.getMessage());
			e.printStackTrace();
		}
		return decouvert;
	}
	/**
	 * Requ�te qui va rechercher qui poss�de un compte �pargene et un compte courant
	 * @return Retourne le nom du titulaire des deux comptes
	 */
	public String compteEpAndCo()
	{
		String titulaire = "";
		String result = "";
		/* Ex�cution d'une requ�te de lecture */
		try
		{
			ResultSet resultat = statement.executeQuery( "SELECT compte_epargne.NOMTITULAIRE AS Epargnant, compte_courant.NOMTITULAIRE AS Client FROM compte_epargne, compte_courant WHERE compte_epargne.NOMTITULAIRE = compte_courant.NOMTITULAIRE;" );

			/* R�cup�ration des donn�es du r�sultat de la requ�te de lecture */
			while ( resultat.next() )
			{
				titulaire = resultat.getString( "Epargnant" );
			    /* Traiter ici les valeurs r�cup�r�es. */
				result = (result + "\n" + titulaire); 
			}
			/*if (titulaire.equals(""))
			{
				System.out.println("Aucun client ne dispose d'un compte �pargne et d'un compte courant.");
			}
			else
			{
				System.out.println(titulaire);
			}*/
			
		}
			catch (SQLException e)
			{
			// TODO Auto-generated catch block
			System.out.println("PROBLEME SELECT " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * Req�te de recherche de tous les clients en d�couvert
	 * @return Le nom de toutes les personnes �tant en d�couvert
	 */
	public String titulaireEnDecouvert()
	{
		double decouvert;
		String titulaire;
		String result = "";
		/* Ex�cution d'une requ�te de lecture */
		try
		{
			ResultSet resultat = statement.executeQuery( "SELECT NOMTITULAIRE, SOLDE  FROM compte_courant WHERE SOLDE < 0;" );

			/* R�cup�ration des donn�es du r�sultat de la requ�te de lecture */
			while ( resultat.next() )
			{
			    decouvert = resultat.getDouble( "SOLDE" );
			    titulaire = resultat.getString( "NOMTITULAIRE" );
			    /* Traiter ici les valeurs r�cup�r�es. */
			    //System.out.println(decouvert + " : " + titulaire);
			    result = (result + "\n" + decouvert+" " + titulaire);
			}
		}
			catch (SQLException e)
			{
			// TODO Auto-generated catch block
			System.out.println("PROBLEME SELECT " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * Requ�te permettant d'afficher l'historique d'un compte
	 * @param num
	 * 		: Envoi du num�ro de compte afin de trouver toutes les op�ration li� au compte en cours de consultation
	 * @return Tous les d�p�ts ou retraits effectu�s par le client
	 */
	public String afficheConsulations(int num)
	{
		double numero;
		String typeOp;
		double montant;
		Date date;
		String result = "";
		/* Ex�cution d'une requ�te de lecture */
		try
		{
			ResultSet resultat = statement.executeQuery( "SELECT NUMERO, TYPEOPERATION, MONTANT, DATE  FROM consultations WHERE NUMERO_COMPTE = " + num +";" );

			/* R�cup�ration des donn�es du r�sultat de la requ�te de lecture */
			while ( resultat.next() )
			{
				numero = resultat.getInt( "NUMERO" );
				typeOp = resultat.getString( "TYPEOPERATION" );
				montant = resultat.getDouble( "MONTANT" );
				date = resultat.getDate( "DATE" );
			    /* Traiter ici les valeurs r�cup�r�es. */
			    //System.out.println(decouvert + " : " + titulaire);
				result = result + "\n" + (String.valueOf(numero) + "   " + typeOp + "   " + String.valueOf(montant) + "   " + String.valueOf(date));
			}
		}
			catch (SQLException e)
			{
			// TODO Auto-generated catch block
			System.out.println("PROBLEME SELECT " + e.getMessage());
			e.printStackTrace();
		}
		//return decouvert;
		return result;
	}
	}
