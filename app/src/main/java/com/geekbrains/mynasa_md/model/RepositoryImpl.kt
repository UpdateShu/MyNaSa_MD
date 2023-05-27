package com.geekbrains.mynasa_md.model

import com.geekbrains.mynasa_md.databinding.FragmentPictureBinding
import com.geekbrains.mynasa_md.utils.Constants.TYPE_HEADER
import com.geekbrains.mynasa_md.utils.Constants.TYPE_NOTE
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepositoryImpl : Repository {
    private val baseUrl = "https://api.nasa.gov/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }

    fun getPictureOfTheDayApi(): PictureOfTheDayAPI {
        return retrofit.create(PictureOfTheDayAPI::class.java)
    }

    fun getDataRepos() : MutableList<Pair<MyNote, Boolean>> = editNotes

    private val _editNotes: MutableList<Pair<MyNote, Boolean>> = mutableListOf()
    private val editNotes : MutableList<Pair<MyNote, Boolean>> get() {
        if (_editNotes.isEmpty())
            _editNotes.addAll(initNotes)
        return _editNotes
    }

    private val initNotes : List<Pair<MyNote, Boolean>> = listOf(
        Pair(MyNote(id = 0,"Планы на субботу", "", TYPE_HEADER), false),
        Pair(MyNote(id = 1,"Подъём в 5 утра", "", TYPE_NOTE), false),
        Pair(MyNote(id = 2,"Утренняя молитва", "", TYPE_NOTE), false),
        Pair(MyNote(id = 3,"Пробежка", "", TYPE_NOTE), false),
        Pair(MyNote(id = 4,"Завтрак на нижней веранде", "", TYPE_NOTE), false),
        Pair(MyNote(id = 5,"Распоряжения для управляющего", "", TYPE_NOTE), false),
        Pair(MyNote(id = 6,"Пойти на субботник", "", TYPE_NOTE), false),
        Pair(MyNote(id = 7,"Выполнить задания по-китайскому языку", "", TYPE_NOTE), false),
        Pair(MyNote(id = 8,"Сходить на митинг", "", TYPE_NOTE), false),
        Pair(MyNote(id = 9,"Провести урок патриотического воспитания", "", TYPE_NOTE), false),
        Pair(MyNote(id = 10,"Приобрести аппартаменты в ОАЭ", "", TYPE_NOTE), false),
        Pair(MyNote(id = 11,"Прослушать лекцию о ЗОЖ", "", TYPE_NOTE), false),
        Pair(MyNote(id = 12,"Любование закатом", "", TYPE_NOTE), false),
        Pair(MyNote(id = 13,"Занятия йогой", "", TYPE_NOTE), false),
        Pair(MyNote(id = 14,"Вечерняя молитва", "", TYPE_NOTE), false),
        Pair(MyNote(id = 15,"Задуть свечи", "", TYPE_NOTE), false))
}