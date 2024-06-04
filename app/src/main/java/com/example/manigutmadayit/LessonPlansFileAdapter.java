package com.example.manigutmadayit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LessonPlansFileAdapter extends RecyclerView.Adapter<LessonPlansFileAdapter.FileViewHolder> {

    private List<LessonPlansFileModel> fileList;
    private OnItemClickListener listener;
    private OnDeleteClickListener deleteListener;

    public interface OnItemClickListener {
        void onItemClick(LessonPlansFileModel file);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(LessonPlansFileModel file);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    public LessonPlansFileAdapter(List<LessonPlansFileModel> fileList) {
        this.fileList = fileList;
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_item, parent, false);
        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {
        LessonPlansFileModel file = fileList.get(position);
        holder.bind(file, listener, deleteListener);
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    public void setFileList(List<LessonPlansFileModel> fileList) {
        this.fileList = fileList;
        notifyDataSetChanged();
    }

    public static class FileViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewFileName;
        private Button buttonDownload;
        private ImageButton buttonDelete; // Corrected to ImageButton

        public FileViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewFileName = itemView.findViewById(R.id.textViewFileName);
            buttonDownload = itemView.findViewById(R.id.buttonDownload);
            buttonDelete = itemView.findViewById(R.id.buttonDelete); // Corrected to ImageButton
        }

        public void bind(final LessonPlansFileModel file, final OnItemClickListener listener, final OnDeleteClickListener deleteListener) {
            textViewFileName.setText(file.getFileName());

            buttonDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(file);
                    }
                }
            });

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deleteListener != null) {
                        deleteListener.onDeleteClick(file);
                    }
                }
            });
        }
    }
}
