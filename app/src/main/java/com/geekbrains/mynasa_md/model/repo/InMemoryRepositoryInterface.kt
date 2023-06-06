package com.geekbrains.mynasa_md.model.repo

import androidx.fragment.app.Fragment

interface InMemoryRepositoryInterface {
    fun getFragmentListInMemory(): List<Fragment>
}