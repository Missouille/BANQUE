package banque;

import java.sql.*;

public class bddConnect
{
	String url = "jdbc:mysql://localhost:3306/banque?user=root&password=''";
    String utilisateur = "root";
    String motDePasse = "";
	Connection connexion = null;
	Statement statement = null;
	ResultSet resultat = null;
	
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
			System.out.println("TUTUUUU");
			System.out.println("Connection problem " + e.getMessage());
		} /*finally {
		    if ( resultat != null ) {
		        try {
		             On commence par fermer le ResultSet 
		            resultat.close();
		        } catch ( SQLException ignore ) {
		        }
		    }
		    if ( statement != null ) {
		        try {
		             Puis on ferme le Statement 
		            statement.close();
		        } catch ( SQLException ignore ) {
		        }
		    }
		    if ( connexion != null ) {
		        try {
		             Et enfin on ferme la connexion 
		            connexion.close();
		        } catch ( SQLException ignore ) {
		        }
		    }
		}*/
	}
	public void insertionCompte(int num, String nom, double solde, int type)
	{
		/* Ex�cution d'une requ�te d'�criture */
        try {
        	System.out.println("num�ro : " + num + " nom : " + nom + " solde : " + solde);
			int statut = statement.executeUpdate( "INSERT INTO compte (NUMERO, NOMTITULAIRE, SOLDE, TYPE) VALUES (" + num + ", " + "'" + nom + "'" + " ," + solde + ", " + type + ");" );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("PROBLEME " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void insertionCompteEpargne(int num, String nom, double solde, int type, double taux)
	{
		/* Ex�cution d'une requ�te d'�criture */
        try {
        	System.out.println("num�ro : " + num + " nom : " + nom + " solde : " + solde + "taux : " + taux);
			int statut = statement.executeUpdate( "INSERT INTO compte_epargne (NUMERO, NOMTITULAIRE, SOLDE, TYPE, TAUX) VALUES (" + num + ", " + "'" + nom + "'" + " ," + solde + "," +type + "," + taux + ");" );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("PROBLEME " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void insertionCompteCourant(int num, String nom, double solde, int type, double decouvertAuto)
	{
		/* Ex�cution d'une requ�te d'�criture */
        try {
        	System.out.println("num�ro : " + num + " nom : " + nom + " solde : " + solde + "d�couvert autoris� : " + decouvertAuto);
			int statut = statement.executeUpdate( "INSERT INTO compte_courant (NUMERO, NOMTITULAIRE, SOLDE, TYPE, DECOUVERTAUTORISE) VALUES (" + num + ", " + "'" + nom + "'" + " ," + solde + "," + type + "," + decouvertAuto + ");" );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("PROBLEME " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void mise_a_jour_taux(int num, double taux)
	{
		/* Ex�cution d'une requ�te d'�criture */
        try {
        	System.out.println("Nouveau taux : " + taux);
			int statut = statement.executeUpdate( "UPDATE compte_epargne SET TAUX = " + taux + " WHERE NUMERO = " + num + ";" );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("PROBLEME UPDATE TAUX " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void mise_a_jour_decouvert(int num, double decouv)
	{
		/* Ex�cution d'une requ�te d'�criture */
        try {
        	System.out.println("Nouveau decouvert : " + decouv);
			int statut = statement.executeUpdate( "UPDATE compte_courant SET DECOUVERTAUTORISE = " + decouv + " WHERE NUMERO = " + num + ";" );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("PROBLEME UPDATE TAUX " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void consulte(int num)
	{
		/* Ex�cution d'une requ�te de lecture */
		try
		{
			ResultSet resultat = statement.executeQuery( "SELECT NUMERO, SOLDE, NOMTITULAIRE  FROM compte WHERE " + num +" = NUMERO;" );

			/* R�cup�ration des donn�es du r�sultat de la requ�te de lecture */
			while ( resultat.next() )
			{
			    int numeroCom = resultat.getInt( "NUMERO" );
			    double soldeCom = resultat.getDouble("SOLDE");
			    String nomTit = resultat.getString("NOMTITULAIRE");

			    /* Traiter ici les valeurs r�cup�r�es. */
			    System.out.println("ICI LE NUM : " + numeroCom);
			    System.out.println("ICI LE SOLDE : " + soldeCom);
			    System.out.println("ICI LE NOM : " + nomTit);
			}
		}
			catch (SQLException e)
			{
			// TODO Auto-generated catch block
			System.out.println("PROBLEME SELECT " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void manipuler(int num, double solde)
	{
		/* Ex�cution d'une requ�te d'�criture */
        try {
        	System.out.println("Nouveau solde : " + solde);
			int statut = statement.executeUpdate( "UPDATE compte SET SOLDE = SOLDE + " + solde + " WHERE NUMERO = " + num + ";" );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("PROBLEME UPDATE TAUX " + e.getMessage());
			e.printStackTrace();
		}
	}
	
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
	
	public double recupTaux(int num)
	{
		double taux = 0;
		/* Ex�cution d'une requ�te de lecture */
		try
		{
			ResultSet resultat = statement.executeQuery( "SELECT TAUX  FROM compte_epargne WHERE " + num +" = NUMERO;" );

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
	}
