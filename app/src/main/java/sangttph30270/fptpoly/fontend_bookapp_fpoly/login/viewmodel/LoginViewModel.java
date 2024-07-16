package sangttph30270.fptpoly.fontend_bookapp_fpoly.login.viewmodel;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.network.RepositoryHome;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.login.network.RepositoryLogin;

public class LoginViewModel {
    private RepositoryLogin repositoryLogin;

    public LoginViewModel(RepositoryLogin repositoryLogin) {
        this.repositoryLogin = repositoryLogin;
    }
}
