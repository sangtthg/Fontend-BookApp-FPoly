package sangttph30270.fptpoly.fontend_bookapp_fpoly.auth.login.viewmodel;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.auth.login.network.RepositoryLogin;

public class LoginViewModel {
    private RepositoryLogin repositoryLogin;

    public LoginViewModel(RepositoryLogin repositoryLogin) {
        this.repositoryLogin = repositoryLogin;
    }
}
