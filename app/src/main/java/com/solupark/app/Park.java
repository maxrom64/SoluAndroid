package com.solupark.app;

public class Park {
    private String address;
    private String codePostal;
    private String ville;
    private String pays;

    @Override
    public String toString() {
        return "Park{" +
                "address='" + address + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", ville='" + ville + '\'' +
                ", pays='" + pays + '\'' +
                '}';
    }

    public Park(String address, String codePostal, String ville, String pays) {
        super();
        this.address = address;
        this.codePostal = codePostal;
        this.ville = ville;
        this.pays = pays;
    }
        public String getAddress() {
            return address;
        }
        public void setAdress(String address) {
            this.address = address;
        }
        public String getCodePostal() {
            return codePostal;
        }
        public void setCodePostal(String codePostal) {
            this.codePostal = codePostal;
        }
        public String getVille() {
            return ville;
        }
        public void setVille(String ville) {
            this.ville = ville;
        }
        public String getPays() {
            return pays;
        }
        public void setPays(String pays) {
            this.pays = pays;
        }
        public String getLocation() {
            return address + ", " + codePostal + ", " + ville + ", " + pays;
    }
        public Park(){}

}
