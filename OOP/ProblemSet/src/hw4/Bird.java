package hw4;

public class Bird {
    public void gulgate(Bird b) {
        System.out.println("BiGulBi");
    }
}

class Falcon extends Bird {
    public void gulgate(Falcon f) {
        System.out.println("FaGulFa");
    }

    public static void main(String[] args) {
        Bird bird = new Falcon();
        Falcon falcon = (Falcon) bird;
        bird.gulgate(falcon);
        falcon.gulgate(falcon);
    }
}


