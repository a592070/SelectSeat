package selectseat

class User {
    Long id
    Long version

    String email
    static hasMany = [orders:OrderList]

    static constraints = {
        email email: true, blank: false, nullable: false, unique: true
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", version=" + version +
                ", email='" + email + '\'' +
                '}';
    }
}
