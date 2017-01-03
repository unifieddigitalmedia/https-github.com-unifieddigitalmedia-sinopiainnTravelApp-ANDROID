package layout;


import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.example.home.sinopiainntravelapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Error_Logging extends BottomSheetDialogFragment {

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


        public void setupDialog(Dialog dialog, int style) {

            TextView error1  ;

            TextView error2  ;

            super.setupDialog(dialog, style);
            View contentView = View.inflate(getContext(), R.layout.fragment_error_logging, null);

            error1 = (TextView) contentView.findViewById(R.id.error1);

            error2 = (TextView) contentView.findViewById(R.id.error2);

           ;

            error1.setText( getArguments().get("error1").toString());

            error2.setText( getArguments().get("error2").toString());

            dialog.setContentView(contentView);

            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();

            CoordinatorLayout.Behavior behavior = params.getBehavior();

            if( behavior != null && behavior instanceof BottomSheetBehavior ) {

                ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);

            }

        }

    }