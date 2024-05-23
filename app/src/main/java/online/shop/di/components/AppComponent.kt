package online.shop.di.components

import dagger.Component
import online.shop.di.modules.NetworkModule
import online.shop.di.modules.NetworkRepoModule
import online.shop.ui.details.DetailsViewModel
import online.shop.ui.list.ListScreenViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, NetworkRepoModule::class])
interface AppComponent {
    val listViewModelFactory: ListScreenViewModel.Factory
    val detailsViewModelFactory: DetailsViewModel.Factory
}