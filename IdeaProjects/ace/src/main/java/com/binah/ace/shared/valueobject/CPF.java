package com.binah.ace.shared.valueobject;

/**
 * Value Object representing a valid CPF.
 *
 * Ensures that every CPF in the system is valid
 * (with correct check digits).
 * Immutable — once created, it cannot be modified.
 *
 * @author Marcos Gustavo
 */
public record CPF(String value) {

    /**
     * Compact constructor that validates the CPF upon creation.
     *
     * @throws IllegalArgumentException if the CPF is invalid
     */
    public CPF {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("CPF cannot be null or empty");
        }

        // Remove formatação (pontos, hífens, espaços)
        String cleanCpf = value.replaceAll("[^0-9]", "");

        if (!isValid(cleanCpf)) {
            throw new IllegalArgumentException("Invalid CPF: " + value);
        }

        // Armazena apenas os números
        value = cleanCpf;
    }

    /**
     * Validates a CPF using the check digit algorithm.
     *
     * @param cpf CPF containing only digits (11 digits)
     * @return {@code true} if the CPF is valid
     */
    private static boolean isValid(String cpf) {
        // Deve ter 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Rejeita CPFs conhecidos como inválidos (todos dígitos iguais)
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            // Calcula primeiro dígito verificador
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int firstDigit = 11 - (sum % 11);
            if (firstDigit >= 10) firstDigit = 0;

            // Calcula segundo dígito verificador
            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            int secondDigit = 11 - (sum % 11);
            if (secondDigit >= 10) secondDigit = 0;

            // Verifica se os dígitos calculados conferem
            return cpf.charAt(9) == Character.forDigit(firstDigit, 10) &&
                    cpf.charAt(10) == Character.forDigit(secondDigit, 10);

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns the formatted CPF (e.g., 123.456.789-10).
     *
     * @return Formatted CPF
     */
    public String getFormatted() {
        return String.format(
                "%s.%s.%s-%s",
                value.substring(0, 3),
                value.substring(3, 6),
                value.substring(6, 9),
                value.substring(9, 11)
        );
    }

    /**
     * Returns the CPF without formatting (digits only).
     *
     * @return Raw CPF value
     */
    @Override
    public String toString() {
        return value;
    }
}