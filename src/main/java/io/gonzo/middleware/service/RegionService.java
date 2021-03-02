package io.gonzo.middleware.service;

import io.gonzo.middleware.web.dto.RegionDTO;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class RegionService {

    private String COMMA_DELIMITER = ",";

    public RegionDTO getByRegionCode(String regionName) {
        List<RegionDTO> regions = readByRegionCode();
        return regions.stream()
                .filter(region -> region.getRegionName().equals(regionName))
                .findAny()
                .orElse(RegionDTO.builder().build());
    }

    private List<RegionDTO> readByRegionCode() {
        File file = new File("src/main/resources/region_code5.csv");
        List<RegionDTO> regions = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lines = line.split(COMMA_DELIMITER);
                String name = lines[0].trim();
                String code = lines[1].trim();
                regions.add(
                        RegionDTO.builder()
                                .regionName(name)
                                .regionCode(code)
                                .build()
                );
            }
            return regions;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return regions;
    }

}
