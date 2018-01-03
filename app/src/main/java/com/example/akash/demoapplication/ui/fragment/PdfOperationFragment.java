package com.example.akash.demoapplication.ui.fragment;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintJob;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.print.PrintHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.akash.demoapplication.R;
import com.example.akash.demoapplication.constant.AppConstant;
import com.example.akash.demoapplication.service.PrintJobMonitorService;
import com.example.akash.demoapplication.utils.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import static android.content.Context.PRINT_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class PdfOperationFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private int position = -1;
    private File file;
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };
    private PrintManager mgr;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lula_bottom_sheet_dialog, container, false);
        position = getArguments().getInt(AppConstant.PDF_POSITION);
        mgr = (PrintManager) getActivity().getSystemService(PRINT_SERVICE);
        v.findViewById(R.id.tv_print).setOnClickListener(this);
        v.findViewById(R.id.tv_share).setOnClickListener(this);
        v.findViewById(R.id.tv_view).setOnClickListener(this);
        return v;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_print:
                file = new File(FileUtils.getOutputMediaFileForPdf().getPath() + File.separator
                        + FileUtils.getOutputMediaFileForPdf().list()[position]);
//                    Intent printIntent = new Intent(getActivity(), PrintDialogActivity.class);
//                    printIntent.setDataAndType(Uri.fromFile(file), "MIME");
//                    printIntent.putExtra("title", "");
//                    startActivity(printIntent);
                PrintManager printManager = (PrintManager) getActivity().getSystemService(Context.PRINT_SERVICE);
                PrintAttributes pdfPrintAttrs = new PrintAttributes.Builder().
                        setMediaSize(PrintAttributes.MediaSize.ISO_A6).
                        setMinMargins(new PrintAttributes.Margins(0, 200, -200, 200)).
                        build();
                String jobName = getString(R.string.app_name) + " Document";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    printManager.print(jobName, pda, pdfPrintAttrs);
                } else {
                    Toast.makeText(getActivity(), "Please update your mobile for this feature", Toast.LENGTH_SHORT).show();
                }

               // doPhotoPrint();

                break;
            case R.id.tv_share:
                sharePdf(position);
                break;
            case R.id.tv_view:
                viewPdf(position);
                break;
        }
        dismiss();

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void doPhotoPrint() {
        PrintHelper photoPrinter = new PrintHelper(getActivity());
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        ArrayList<Bitmap> list = pdfToBitmap();
//        for (int i=0;i<list.size();i++)
//        {
            photoPrinter.printBitmap("droids.jpg - test print", list.get(1));

//        }
    }

    private PrintJob print(String name, PrintDocumentAdapter adapter,
                           PrintAttributes attrs) {
        getActivity().startService(new Intent(getActivity(), PrintJobMonitorService.class));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return (mgr.print(name, adapter, attrs));
        }
        return null;
    }

    private void viewPdf(int position) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(FileUtils.getOutputMediaFileForPdf().getPath() + File.separator
                + FileUtils.getOutputMediaFileForPdf().list()[position])), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);

    }

    private void sharePdf(int position) {
        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.setType("application/pdf");
        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(FileUtils.getOutputMediaFileForPdf().getPath() + File.separator
                + FileUtils.getOutputMediaFileForPdf().list()[position])));
        // share.setPackage("com.whatsapp");
        startActivity(share);
    }

    PrintDocumentAdapter pda = new PrintDocumentAdapter() {


        public PrintedPdfDocument mPdfDocument;

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
            InputStream input = null;
            OutputStream output = null;


            try {
                input = new FileInputStream(file);
                output = new FileOutputStream(destination.getFileDescriptor());
                byte[] buf = new byte[1024];
                int bytesRead;
                while ((bytesRead = input.read(buf)) > 0) {
                    output.write(buf, 0, bytesRead);
                }
                callback.onWriteFinished(new PageRange[]{
                        PageRange.ALL_PAGES
                });

            } catch (FileNotFoundException ee) {
                //Catch exception
            } catch (Exception e) {
                //Catch exception
            } finally {
                try {
                    input.close();
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

//
//            // Iterate over each page of the document,
//            // check if it's in the output range.
//            for (int i = 0; i < totalPages; i++) {
//                // Check to see if this page is in the output range.
//                if (containsPage(pageRanges, i)) {
//                    // If so, add it to writtenPagesArray. writtenPagesArray.size()
//                    // is used to compute the next output page index.
//                    writtenPagesArray.append(writtenPagesArray.size(), i);
//                    PdfDocument.Page page = mPdfDocument.startPage(i);
//
//                    // check for cancellation
//                    if (cancellationSignal.isCancelled()) {
//                        callback.onWriteCancelled();
//                        mPdfDocument.close();
//                        mPdfDocument = null;
//                        return;
//                    }
//
//                    // Draw page content for printing
//                    drawPage(page);
//
//                    // Rendering is complete, so page can be finalized.
//                    mPdfDocument.finishPage(page);
//                }
//            }
//
//            // Write PDF document to file
//            try {
//                mPdfDocument.writeTo(new FileOutputStream(
//                        destination.getFileDescriptor()));
//            } catch (IOException e) {
//                callback.onWriteFailed(e.toString());
//                return;
//            } finally {
//                mPdfDocument.close();
//                mPdfDocument = null;
//            }
//            PageRange[] writtenPages = computeWrittenPages();
//            // Signal the print framework the document is complete
//            callback.onWriteFinished(writtenPages);

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {
//            if (cancellationSignal.isCanceled()) {
//                callback.onLayoutCancelled();
//                return;
//            }
//            PrintDocumentInfo mPdfDocument = new PrintDocumentInfo.Builder("Name of file")
//                    .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
//                    .build();
//            callback.onLayoutFinished(pdi, true);
            mPdfDocument = new PrintedPdfDocument(getActivity(), newAttributes);
            Log.e("margin", "   " + oldAttributes.getMinMargins())
            ;
            Log.e("margin", "   " + newAttributes.getMinMargins())
            ;
            // Respond to cancellation request
            if (cancellationSignal.isCanceled()) {
                callback.onLayoutCancelled();
                return;
            }

            // Compute the expected number of printed pages
            PrintDocumentInfo info = new PrintDocumentInfo
                    .Builder("print_output.pdf")
                    .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .build();
            // Content layout reflow is complete
            callback.onLayoutFinished(info, false);

        }
    };
       @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
      private ArrayList<Bitmap> pdfToBitmap() {
           ArrayList<Bitmap> bitmaps = new ArrayList<>();

           try {
               PdfRenderer renderer = new PdfRenderer(ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY));

               Bitmap bitmap;
               final int pageCount = renderer.getPageCount();
               for (int i = 0; i < pageCount; i++) {
                   PdfRenderer.Page page = renderer.openPage(i);

                   int width = getResources().getDisplayMetrics().densityDpi / 72 * page.getWidth();
                   int height = getResources().getDisplayMetrics().densityDpi / 72 * page.getHeight();
                   bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

                   page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

                   bitmaps.add(bitmap);

                   // close the page
                   page.close();

               }

               // close the renderer
               renderer.close();
           } catch (Exception ex) {
               ex.printStackTrace();
           }

           return bitmaps;

       }


}