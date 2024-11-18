package controller;

import enums.RecordFileType;
import model.AppointmentOutcomeRecord;
import model.Diagnosis;
import model.PrescribedMedication;
import repository.AppointmentOutcomeRecordRepository;
import repository.DiagnosisRepository;
import repository.PrescribedMedicationRepository;

import java.util.ArrayList;
import java.util.HashMap;

public class PrescribedMedicineController {
    public static String generateRecordID(RecordFileType recType) {
        String prefix = "";
        int nextId = 0;
        // Set the prefix based on the record type
        switch (recType) {
            case PRESCRIBED_RECORDS:
                prefix = "PR-";
                break;
            default:
                break;
        }

        // Find the highest ID currently in the repository based on the record type
        switch (recType) {
            case PRESCRIBED_RECORDS:
                HashMap<String, ArrayList<PrescribedMedication>> prescribedRepo = PrescribedMedicationRepository.diagnosisToMedicationsMap;
                for (ArrayList<PrescribedMedication> records : prescribedRepo.values()) {
                    for (PrescribedMedication record : records) {
                        String id = record.getPrescribedMedID();
                        if (id.startsWith(prefix)) {
                            try {
                                // Extract the numeric part after the prefix and parse it
                                int currentId = Integer.parseInt(id.substring(prefix.length()));
                                nextId = Math.max(nextId, currentId + 1); // Increment for the next ID
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid ID format: " + id);
                            }
                        }
                    }
                }
                break;

            default:
                System.out.println("Invalid record type.");
                break;
        }

        // Format the next ID with leading zeros (e.g., "AO001")
        return String.format("%s%03d", prefix, nextId);
    }
}
