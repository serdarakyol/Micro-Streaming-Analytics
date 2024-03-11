package com.example.MSA.dto;

import java.util.List;

public record DataRecordDTO(String version, String device, String path, String trustedBoot,
        List<DataStreamDTO> datastreams) {
    public static class Builder {
        private String version;
        private String device;
        private String path;
        private String trustedBoot;
        private List<DataStreamDTO> datastreams;

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public Builder device(String device) {
            this.device = device;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder trustedBoot(String trustedBoot) {
            this.trustedBoot = trustedBoot;
            return this;
        }

        public Builder dataStreamDTOs(List<DataStreamDTO> datastreams) {
            this.datastreams = datastreams;
            return this;
        }

        public DataRecordDTO build() {
            return new DataRecordDTO(version, device, path, trustedBoot, datastreams);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
