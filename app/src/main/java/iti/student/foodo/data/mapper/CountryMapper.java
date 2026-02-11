package iti.student.foodo.data.mapper;

import java.util.ArrayList;
import java.util.List;

import iti.student.foodo.data.model.domain.Country;
import iti.student.foodo.data.model.dto.CountriesItem;

public class CountryMapper {

    public static Country map(CountriesItem item) {
        return new Country(
                item.getStrArea(),
                CountriesItem.getFlagUrl(item.getStrArea())
        );
    }

    public static List<Country> mapList(List<CountriesItem> items) {
        List<Country> countries = new ArrayList<>();
        if (items == null) return countries;

        for (CountriesItem item : items) {
            countries.add(map(item));
        }
        return countries;
    }
}
