package se.kth.iv1201.group4.recruitment.dataReader.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class JsonToMapReaderTest {
    @Test
    void testIfJsonFileIsSuccessfullyConvertedToListOfMaps(){
        noNulls("applicants.json","personId","name","surname","ssn","email","username","password");
        noNulls("recruiters.json","personId","name","surname","ssn","email","username","password");
        noNulls("availabilities.json","availabilityId","personId","fromDate","toDate");
        noNulls("competencies.json","competenceId","name");
        noNulls("competenceProfiles.json","competenceId","competenceProfileId","personId","yearsOfExperience");
    }
    private void noNulls(final String fileName, String...fields){
        List<Map<String,Object>> contents = new ArrayList<Map<String,Object>>();
        contents = new JsonToMapReader().convertJsonFileToMap("data/"+fileName);
        for(Map<String, Object> map : contents)
            for(String field : fields)
                assertNotNull(map.get(field));
    }
}
