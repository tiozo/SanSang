package models.Address;

public class AddressDTO {
    private final int addressID;
    private final String street;
    private final String city;
    private final String state;
    private final String postalCode;
    private final String country;

    private AddressDTO(Builder builder) {
        this.addressID = builder.addressID;
        this.street = builder.street;
        this.city = builder.city;
        this.state = builder.state;
        this.postalCode = builder.postalCode;
        this.country = builder.country;
    }

    // ---------- Builder Pattern ----------
    public static class Builder {
        // Required parameters
        private final String street;
        private final String city;
        private final String country;

        // Optional parameters
        private int addressID = 0; // default for new addresses
        private String state = "";
        private String postalCode = "";

        public Builder(AddressDTO item) {
            this.addressID = item.addressID;
            this.street = item.street;
            this.city = item.city;
            this.state = item.state;
            this.postalCode = item.postalCode;
            this.country = item.country;
        } 
        
        public Builder(String street, String city, String country) {
            if (street == null || street.isEmpty()) {
                throw new IllegalArgumentException("Street cannot be null or empty");
            }
            if (city == null || city.isEmpty()) {
                throw new IllegalArgumentException("City cannot be null or empty");
            }
            if (country == null || country.isEmpty()) {
                throw new IllegalArgumentException("Country cannot be null or empty");
            }
            
            this.street = street;
            this.city = city;
            this.country = country;
        }

        public Builder withAddressID(int addressID) {
            if (addressID < 0) {
                throw new IllegalArgumentException("Address ID must be positive");
            }
            this.addressID = addressID;
            return this;
        }

        public Builder withState(String state) {
            this.state = state != null ? state : "";
            return this;
        }

        public Builder withPostalCode(String postalCode) {
            this.postalCode = postalCode != null ? postalCode : "";
            return this;
        }

        public AddressDTO build() {
            return new AddressDTO(this);
        }
    }

    // ---------- Factory Method ----------
    public static Builder builder(String street, String city, String country) {
        return new Builder(street, city, country);
    }
    
    // ---------- Getters ----------
    public int getAddressID() { return addressID; }
    public String getStreet() { return street; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getPostalCode() { return postalCode; }
    public String getCountry() { return country; }

    // ---------- toString ----------
    @Override
    public String toString() {
        return "AddressDTO{" +
                "addressID=" + addressID +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddressDTO that = (AddressDTO) o;

        if (addressID != that.addressID) return false;
        if (!street.equals(that.street)) return false;
        if (!city.equals(that.city)) return false;
        if (!country.equals(that.country)) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        return postalCode != null ? postalCode.equals(that.postalCode) : that.postalCode == null;
    }

    @Override
    public int hashCode() {
        int result = addressID;
        result = 31 * result + street.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + country.hashCode();
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        return result;
    }   
}