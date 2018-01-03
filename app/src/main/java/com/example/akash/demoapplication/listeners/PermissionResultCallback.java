package com.example.akash.demoapplication.listeners;

import java.util.ArrayList;

/**
 * Created by akash on 26/12/17.
 */

public interface PermissionResultCallback {
    void PermissionGranted(int request_code);
    void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions);
    void PermissionDenied(int request_code);
    void NeverAskAgain(int request_code);
}
