package com.graduation.hp.app.di.component;

import com.graduation.hp.app.di.module.FragmentModule;
import com.graduation.hp.app.di.module.GeneralModule;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.app.di.scope.FragmentScope;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.ui.navigation.article.detail.ArticleDetailFragment;
import com.graduation.hp.ui.navigation.attention.AttentionTabFragment;
import com.graduation.hp.ui.navigation.constitution.detail.InvitationDetailFragment;
import com.graduation.hp.ui.navigation.constitution.list.ConstitutionListFragment;
import com.graduation.hp.ui.navigation.article.comment.ArticleCommentFragment;
import com.graduation.hp.ui.navigation.user.UserTabFragment;
import com.graduation.hp.ui.navigation.article.list.ArticleListFragment;
import com.graduation.hp.ui.navigation.invitation.InvitationTabFragment;
import com.graduation.hp.ui.navigation.user.center.UserArticleFragment;
import com.graduation.hp.ui.navigation.user.center.UserPostFragment;
import com.graduation.hp.ui.question.QuestionListFragment;
import com.graduation.hp.ui.search.SearchResultFragment;
import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Component;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Create by Ning on 2019/04/25
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = {GeneralModule.class, FragmentModule.class})
public interface FragmentComponent {

    RepositoryHelper repositoryHelper();

    Items items();

    MultiTypeAdapter adapter();

    RxPermissions rxPermission();

    void inject(ArticleListFragment newsListFragment);

    void inject(AttentionTabFragment attentionTabFragment);

    void inject(UserTabFragment userCenterFragment);

    void inject(InvitationTabFragment postTabFragment);

    void inject(UserPostFragment userPostFragment);

    void inject(UserArticleFragment userNewsFragment);

    void inject(ArticleDetailFragment newsDetailFragment);

    void inject(ArticleCommentFragment newsCommentFragment);

    void inject(SearchResultFragment searchResultFragment);

    void inject(ConstitutionListFragment constitutionListFragment);

    void inject(InvitationDetailFragment invitationDetailFragment);

    void inject(QuestionListFragment questionListFragment);
}


