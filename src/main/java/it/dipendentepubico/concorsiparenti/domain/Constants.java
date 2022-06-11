package it.dipendentepubico.concorsiparenti.domain;

public class Constants {
    public enum EXPORT_TYPE {
        CSV,
        XLSX
    }

    public enum ESettingsCodes {
        ALLINEAMENTO_IN_CORSO("RUNNING_ALIGN");

        private String code;

        ESettingsCodes(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    public enum ESettingsAllineamentoValues {
        SI("Y"),
        NO("N");

        private String code;

        ESettingsAllineamentoValues(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

}
