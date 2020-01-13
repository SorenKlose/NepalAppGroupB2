package com.example.nepalappgroupb2.Recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nepalappgroupb2.R;
import com.pdfview.PDFView;

public class RecipePdfViewFrag extends Fragment {

    private PDFView pdfView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.frag_recipe_pdf, container, false);

        pdfView = layout.findViewById(R.id.recipe_pdfview_test);
        pdfView.fromAsset("test_pdf.pdf").show();

        return layout;
    }

}
