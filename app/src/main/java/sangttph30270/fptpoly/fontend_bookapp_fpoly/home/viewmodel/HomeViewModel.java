package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.viewmodel;

import androidx.annotation.NonNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.HomeModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.network.RepositoryHome;

public class HomeViewModel {
    private RepositoryHome repositoryHome;

    public HomeViewModel(RepositoryHome repositoryHome) {
        this.repositoryHome = repositoryHome;
    }

}
