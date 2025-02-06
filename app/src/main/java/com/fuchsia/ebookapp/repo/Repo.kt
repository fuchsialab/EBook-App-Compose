package com.fuchsia.ebookapp.repo

import com.fuchsia.ebookapp.common.BOOK_PATH
import com.fuchsia.ebookapp.common.CATEGORY_PATH
import com.fuchsia.ebookapp.common.ResultState
import com.fuchsia.ebookapp.data.BookCategoryModels
import com.fuchsia.ebookapp.data.BookModels
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import com.google.firebase.database.getValue
import kotlinx.coroutines.channels.awaitClose
import javax.inject.Inject

class Repo @Inject constructor(

    val firebaseDatabase: FirebaseDatabase
) {

    fun getAllBooks(): Flow<ResultState<List<BookModels>>> = callbackFlow {
        trySend(ResultState.Loading)

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var items: List<BookModels> = emptyList()
                items = dataSnapshot.children.map { value->
                    value.getValue<BookModels>()!!
                }
                trySend(ResultState.Success(items))

            }

            override fun onCancelled(databaseError: DatabaseError) {
                trySend(ResultState.Error(databaseError.message))

            }

        }
        firebaseDatabase.getReference(BOOK_PATH).addValueEventListener(postListener)

        awaitClose {
            close()
        }

    }
    fun getAllCategory(): Flow<ResultState<List<BookCategoryModels>>> = callbackFlow {
        trySend(ResultState.Loading)

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var items: List<BookCategoryModels> = emptyList()
                items = dataSnapshot.children.map { value->
                    value.getValue<BookCategoryModels>()!!
                }
                trySend(ResultState.Success(items))

            }

            override fun onCancelled(databaseError: DatabaseError) {
                trySend(ResultState.Error(databaseError.message))

            }

        }
        firebaseDatabase.getReference(CATEGORY_PATH).addValueEventListener(postListener)

        awaitClose {
            close()
        }

    }

    fun getAllBooksByCategory(category: String): Flow<ResultState<List<BookModels>>> = callbackFlow {
        trySend(ResultState.Loading)

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var items: List<BookModels> = emptyList()
                items = dataSnapshot.children.filter {
                    it.getValue<BookModels>()?.BookCategory  == category
                }.map {
                    it.getValue<BookModels>()!!
                }
                trySend(ResultState.Success(data = items))

            }

            override fun onCancelled(databaseError: DatabaseError) {
                trySend(ResultState.Error(databaseError.message))

            }

        }
        firebaseDatabase.reference.child(BOOK_PATH).addValueEventListener(postListener)

        awaitClose {
            close()
        }
    }
}