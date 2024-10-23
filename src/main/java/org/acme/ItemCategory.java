package org.acme;

import java.util.StringJoiner;

public class ItemCategory {

    public String category;
    public String subcategory;

    @Override
    public String toString() {
        return new StringJoiner(", ", ItemCategory.class.getSimpleName() + "[", "]")
            .add("category='" + category + "'")
            .add("subcategory='" + subcategory + "'")
            .toString();
    }
}
