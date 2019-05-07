package com.graduation.hp.app.di.component;

import com.graduation.hp.app.di.module.FragmentModule;
import com.graduation.hp.app.di.module.GeneralModule;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.app.di.scope.FragmentScope;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.ui.navigation.attention.AttentionTabFragment;
import com.graduation.hp.ui.navigation.constitution.detail.InvitationDetailFragment;
import com.graduation.hp.ui.navigation.constitution.list.ConstitutionListFragment;
import com.graduation.hp.ui.navigation.news.comment.NewsCommentFragment;
import com.graduation.hp.ui.navigation.news.detail.NewsDetailFragment;
import com.graduation.hp.ui.navigation.user.UserTabFragment;
import com.graduation.hp.ui.navigation.news.list.NewsListFragment;
import com.graduation.hp.ui.navigation.post.PostTabFragment;
import com.graduation.hp.ui.navigation.user.center.UserNewsFragment;
import com.graduation.hp.ui.navigation.user.center.UserPostFragment;
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

    void inject(NewsListFragment newsListFragment);

    void inject(AttentionTabFragment attentionTabFragment);

    void inject(UserTabFragment userCenterFragment);

    void inject(PostTabFragment postTabFragment);

    void inject(UserPostFragment userPostFragment);

    void inject(UserNewsFragment userNewsFragment);

    void inject(NewsDetailFragment newsDetailFragment);

    void inject(NewsCommentFragment newsCommentFragment);

    void inject(SearchResultFragment searchResultFragment);

    void inject(ConstitutionListFragment constitutionListFragment);

    void inject(InvitationDetailFragment invitationDetailFragment);
}
