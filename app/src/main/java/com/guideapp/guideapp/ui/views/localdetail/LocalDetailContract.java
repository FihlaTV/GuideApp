package com.guideapp.guideapp.ui.views.localdetail;

import android.content.Context;
import android.support.v4.app.LoaderManager;

import com.guideapp.guideapp.model.LocalDetail;

import java.util.List;

interface LocalDetailContract {
    interface View {
        void showFavoriteYes();

        void showFavoriteNo();

        void showSnackbarRemoveFavorite();

        void showSnackbarSaveFavorite();

        void showLocalDetail(List<LocalDetail> localDetails);

        Context getContext();

        void showTitle(String description);

        void showImage(String imagePath);
    }

    interface Presenter {
        void loadLocal(LoaderManager loaderManager);

        void saveOrRemoveFavorite();
    }
}
