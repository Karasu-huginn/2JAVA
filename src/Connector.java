public class Connector {

    public Connector() {
        //todo init
    }

    public Boolean is_connected() {
        //todo send DB simple read
        //todo check if DB responds
        return true;
    }

    public Boolean is_email_available(){
        //todo send DB read with email
        //todo check for matching emails
        return true;
    }

    public void create_account() {
        //todo hash password
        //todo send DB create with infos
        //todo display (return ?) success/error
    }
}
