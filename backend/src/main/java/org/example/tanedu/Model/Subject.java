package org.example.tanedu.Model;

import java.text.Normalizer;
import java.util.Locale;

public enum Subject {
    FIZIKA,
    TESTNEVELÉS,
    MATEMATIKA,
    ANGOL,
    NÉMET,
    MAGYAR_NYELVTAN,
    MAGYAR_IRODALOM,
    TÖRTÉNELEM,
    INFORMATIKA,
    BIOLÓGIA,
    KÉMIA,
    FÖLDRAJZ;

    private static String normalize(String s) {
        if (s == null) return "";
        // trim, uppercase, replace spaces/hyphens with underscore
        s = s.trim().toUpperCase(Locale.ROOT).replace(' ', '_').replace('-', '_');
        // remove diacritics
        s = Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}+", "");
        return s;
    }

    public static Subject fromString(String input) {
        String normalizedInput = normalize(input);
        for (Subject subj : values()) {
            if (normalize(subj.name()).equals(normalizedInput)) {
                return subj;
            }
        }
        throw new IllegalArgumentException("Invalid subject: " + input);
    }
}
