package SP_Homework_11_by_June_14;
class Country implements WelcomesFreelancers {
    String countryName;

    @Override
    public void visitCountry() {
        System.out.println("Welcome to " + countryName + "! This will be a GREAT vacation!");
    }

    @Override
    public void moveToCountry() {
        System.out.println("You have immigrated to " + countryName + ". No more enormous heating bills!");
    }
}

class Province extends Country implements GrowsTastyFood {
    String provinceName;

    @Override
    public void growFruit() {
        System.out.println("The province of " + provinceName + "has grown some nice fruit for you! De-e-elicous!");
    }

    @Override
    public void growPiggies() {
        System.out.println("The province of " + provinceName + "has grown a sweet round piggy for you! Oink-oink!");
    }
}

class City extends Province implements HasLandmarks {
    String cityName;

    @Override
    public void seeLandmark() {
        System.out.println("It's a must-see if you happen to find yourself in " + cityName + "!");
    }

    @Override
    public void takeLandmarkPhoto() {
        System.out.println("You will relive the memories of " + cityName + " whenever you see these photos!");
    }
}


public class ThaiGeographyClassStructure {
    public static void main(String[] args) {
        Country Vietnam = new Country();
        Country Thailand = new Country();
        Province Bangkok = new Province();
        City BangkokCity = new City();
        City Nonthaburi = new City();
        City SamutPrakan = new City();
        Province SuratThani = new Province();
        City SuratThaniCity = new City();
        City KohSamui = new City();
        City Phunphin = new City();
        Province Ayutthaya = new Province();
        City AyutthayaCity = new City();
        City Bang_Pa_in = new City();
        City Phachi = new City();
    }
}
interface HasLandmarks {
void seeLandmark();
void takeLandmarkPhoto();
}

interface WelcomesFreelancers {
    void visitCountry();
    void moveToCountry();
}

interface GrowsTastyFood {
    void growFruit();
    void growPiggies();
}
