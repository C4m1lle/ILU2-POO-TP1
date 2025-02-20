package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum,int nbEtal) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtal);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	public String installerVendeur(Gaulois vendeur, String produit,int
			nbProduit) {
		StringBuilder chaine = new StringBuilder();
		int i = marche.trouverEtalLibre();
		marche.utiliserEtal(i, vendeur, produit, nbProduit);
		chaine.append(vendeur.getNom()+" cherche un endroit pour vendre "+nbProduit+" "+ produit
				+ "\n Le vendeur "+vendeur.getNom()+ " vend des "+produit+ " à l'étal n°" + (i+1)+".");
		return chaine.toString();
	}
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] tab = marche.trouverEtals(produit);
		switch(tab.length) {
		case 0:{
			chaine.append("Il n'y a pas de vendeur qui propose des fleurs au marché.");
		}break;
		case 1:{
			chaine.append("Seul le vendeur "+tab[0].getVendeur().getNom()+" propose des "+produit+ " au marché");
		}break;
		default:{
			chaine.append("Les vendeurs qui proposent des "+produit+" sont :\n");
			for(int i = 0; i<tab.length;i++) {
				chaine.append("- "+tab[i].getVendeur().getNom()+"\n");
			}
		}break;
		}

		return chaine.toString();
	}
	static private class Marche {
		private Etal[] etals;
		
		
		public Marche(int nbEtal) {
			etals = new Etal[nbEtal];
			for(int i = 0; i<nbEtal;i++) {
				etals[i] = new Etal();
			}
		}
		public void utiliserEtal(int indiceEtal, Gaulois vendeur,
				String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		public int trouverEtalLibre() {
			int i = 0;
			while(i<etals.length && etals[i].isEtalOccupe()) {
				i++;
			}
			if(i==etals.length) {
				return -1;
			}
			return i;
		}
		public Etal[] trouverEtals(String produit) {
			int cpt = 0;
			for(int i = 0; i<etals.length;i++) {
				if(etals[i].contientProduit(produit)) {
					cpt++;
				}
			}
			Etal[] tab = new Etal[cpt];
			for(int i = 0; i<etals.length;i++) {
				if(etals[i].contientProduit(produit)) {
					tab[tab.length-1] = etals[i];
				}
			}
			return tab;
		}
		public Etal trouverVendeur(Gaulois gaulois) {
			int i = 0;
			while(i<etals.length && etals[i].getVendeur()!=gaulois) {
				i++;
			}
			if(i==etals.length) {
				return null;
			}
			return etals[i];
		}
		 public void afficherMarche() {
			int cpt = 0;
			int nbEtalVide;
			for(int i = 0; i<etals.length;i++) {
				if(etals[i].isEtalOccupe()) {
					etals[i].afficherEtal();
					cpt++;
				}
			}
			nbEtalVide =etals.length-cpt;
			if(nbEtalVide > 0) {
				System.out.println("Il reste " +nbEtalVide + 
						" étals non utilisés dans le marché.\n");
			}
		}
	}
	
}