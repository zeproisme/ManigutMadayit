package com.example.manigutmadayit;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.ArrayList;
import java.util.List;

public class LessonPlans extends AppCompatActivity {
    private FloatingActionButton btnPickFile;
    private Button back;
    private Button btnDownloadFile;
    private RecyclerView recyclerView;
    private LessonPlansFileAdapter fileAdapter;
    private StorageReference reference;
    private DatabaseReference filesRef;

    private ActivityResultLauncher<String> filePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null) {
                        showFileNameInputDialog(result);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_plans);

        btnPickFile = findViewById(R.id.btnPickFile);
        back = findViewById(R.id.backbutton);
        btnDownloadFile = findViewById(R.id.btnDownloadFile);
        reference = FirebaseStorage.getInstance().getReference().child("Documents");
        filesRef = FirebaseDatabase.getInstance("https://manigut-madayit-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("files");
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fileAdapter = new LessonPlansFileAdapter(new ArrayList<>());
        recyclerView.setAdapter(fileAdapter);
        recyclerView.addItemDecoration(new MarginItemDecorations(16));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
                finish();
            }
        });

        btnPickFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filePickerLauncher.launch("*/*");
            }
        });

        fileAdapter.setOnItemClickListener(new LessonPlansFileAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(LessonPlansFileModel file) {
                startDownload(file.getFileUrl(), file.getFileName());
            }
        });

        loadFileListFromFirebase();
    }

    private void uploadFile(Uri fileUri, String fileName) {
        if (fileUri != null) {
            String fileExtension = ".docx"; // Or get the file extension dynamically if needed
            String fullFileName = fileName + fileExtension;
            StorageReference fileRef = reference.child(fullFileName);

            fileRef.putFile(fileUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    saveFileMetadata(fullFileName, uri.toString());
                                }
                            });
                            Toast.makeText(LessonPlans.this, "File uploaded successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LessonPlans.this, "File upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void saveFileMetadata(String fileName, String downloadUrl) {
        String fileId = filesRef.push().getKey();

        if (fileId != null) {
            filesRef.child(fileId).setValue(new LessonPlansFileModel(fileName, downloadUrl, "unknown"))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(LessonPlans.this, "File metadata saved successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LessonPlans.this, "Error saving file metadata: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    });
        } else {
            Toast.makeText(LessonPlans.this, "Error generating file ID", Toast.LENGTH_SHORT).show();
        }
    }

    private void showFileNameInputDialog(Uri fileUri) {
        // Create an AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter File Name");

        // Set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String fileName = input.getText().toString();
                if (!fileName.isEmpty()) {
                    uploadFile(fileUri, fileName);
                } else {
                    Toast.makeText(LessonPlans.this, "File name cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
    private void deleteFile(LessonPlansFileModel file) {
        StorageReference fileRef = reference.child(file.getFileName());
        fileRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                filesRef.child(file.getFileName()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(LessonPlans.this, "File deleted successfully", Toast.LENGTH_SHORT).show();
                        loadFileListFromFirebase();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LessonPlans.this, "Error deleting file metadata: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LessonPlans.this, "Error deleting file: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void startDownload(String fileUrl, String fileName) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileUrl));
        request.setTitle(fileName);
        request.setDescription("Downloading file...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadManager != null) {
            downloadManager.enqueue(request);
        } else {
            Toast.makeText(LessonPlans.this, "Download manager is not available", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFileListFromFirebase() {
        filesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.exists()) {
                    List<LessonPlansFileModel> fileList = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        LessonPlansFileModel file = snapshot.getValue(LessonPlansFileModel.class);
                        fileList.add(file);
                    }
                    fileAdapter.setFileList(fileList);
                } else {
                    Log.d("FirebaseDebug", "No data available");
                    // Handle the case when there is no data available
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LessonPlans.this, "Error loading files: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
