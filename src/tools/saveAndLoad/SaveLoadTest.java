package tools.saveAndLoad;

import java.util.ArrayList;

public class SaveLoadTest {
    
    public transient ArrayList<String> testStrings = new ArrayList<>();
    
    public int testInt;
    public ArrayList<SaveLoadTest> rekursions = new ArrayList<>();
    
    public SaveLoadTest() {
        super();
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((rekursions == null) ? 0 : rekursions.hashCode());
        result = prime * result + testInt;
        result = prime * result + ((testStrings == null) ? 0 : testStrings.hashCode()); 
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SaveLoadTest other = (SaveLoadTest) obj;
        if (rekursions == null) {
            if (other.rekursions != null)
                return false;
        } else if (!rekursions.equals(other.rekursions))
            return false;
        if (testInt != other.testInt)
            return false;

        return true;
    }
    @Override
    public String toString() {
        return "SaveLoadTest [testStrings=" + testStrings + ", testInt=" + testInt + ", rekursions=" + rekursions + "]";
    }
    
    

}
