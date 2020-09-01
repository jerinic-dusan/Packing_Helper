package rs.raf.projekat2.packinghelper.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.projekat2.packinghelper.presentation.viewmodel.SuitcaseViewModel

val suitcaseModule = module {
    viewModel { SuitcaseViewModel() }
}