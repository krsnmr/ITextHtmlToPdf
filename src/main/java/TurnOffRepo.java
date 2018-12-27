import java.util.HashMap;
import java.util.Map;

/**
 * СХЕМА выключения водопроводной линии
 */

public class TurnOffRepo {


    /**
     * Получить объект в виде HashMap
     */
    public static Map<String, String> GetTurnOffItem() {

        Map<String, String> map = new HashMap<>();

        map.put("edge_diameter","aaa01");
        map.put("address","aaa02");
        map.put("from_address", "aaa03");
        map.put("to_address", "aaa04");
        map.put("to_close_valve_ids", "aaa05");
        map.put("valves_count", "aaa06");
        map.put("no_water_house_inputs_ids", "aaa07");
        map.put("no_water_hydrant_ids", "aaa08");
        map.put("details_off_fio", "aaa09");
        map.put("details_off_date", "aaa10");
        map.put("details_off_valve_ids", "aaa11");
        map.put("details_off_turns_count", "aaa12");
        map.put("details_off_dispatcher", "aaa13");
        map.put("details_on_fio", "aaa14");
        map.put("details_on_date", "aaa15");
        map.put("details_on_valve_ids", "aaa16");
        map.put("details_on_turns_count", "aaa17");
        map.put("details_on_dispatcher", "aaa18");
        map.put("social_important_buildings", "aaa19");
        map.put("water_dispense_address", "aaa20");
        map.put("top_address", "aaa21");
        map.put("additional_info", "aaa22");
        map.put("signatures_author", "aaa23");
        map.put("signatures_date", "aaa24");
        map.put("signatures_district", "aaa25");
        map.put("signatures_district_boss", "aaa26");


        return map;
    }

}
