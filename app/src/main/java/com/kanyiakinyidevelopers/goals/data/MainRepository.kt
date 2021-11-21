package com.kanyiakinyidevelopers.goals.data

import com.google.firebase.database.FirebaseDatabase
import com.kanyiakinyidevelopers.goals.models.Goal
import com.kanyiakinyidevelopers.goals.utils.Resource
import com.kanyiakinyidevelopers.goals.utils.safeCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.kanyiakinyidevelopers.goals.models.Goal
import com.kanyiakinyidevelopers.goals.models.User
import com.kanyiakinyidevelopers.goals.utils.Resource
import com.kanyiakinyidevelopers.goals.utils.safeCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MainRepository {
    private val databaseReference = FirebaseDatabase.getInstance().reference

    suspend fun addGoal(goalTitle:String,goalDescription:String,goalColor:String):Resource<Any>{
        return withContext(Dispatchers.IO){
            safeCall {

                val result = FirebaseAuth.getInstance().currentUser?.uid.toString()
                var posterr:String? = null

                val mPoster = databaseReference.child("users").get().await()
                for (i in mPoster.children){
                    val user = i.getValue(User::class.java)

                    if (user != null) {
                        if (user.userId!! == result){
                             posterr = user.name
                        }

                    }
                }

                val dateTimeSec = System.currentTimeMillis()


                val goal = Goal(goalTitle,goalDescription,goalColor,dateTimeSec.toString(),posterr,false)
                databaseReference.child("goals").push().setValue(goal).await()
                Resource.Success(goal)
            }
        }
    }

    private val databaseReference = FirebaseDatabase.getInstance().reference

    suspend fun getGoals(): Resource<List<Goal>> {
        return withContext(Dispatchers.IO) {

            val goalsList = ArrayList<Goal>()

            safeCall {
                val goals = databaseReference.child("goals").get().await()

                for (goal in goals.children) {
                    val result = goal.getValue(Goal::class.java)
                    goalsList.add(result!!)
                }

                Resource.Success(goalsList)
            }
        }
    }

    suspend fun getAchievedGoals(): Resource<List<Goal>> {
        return withContext(Dispatchers.IO) {

            val achievedGoalsList = ArrayList<Goal>()

            safeCall {
                val goals = databaseReference.child("achieved_goals").get().await()

                for (goal in goals.children) {
                    val result = goal.getValue(Goal::class.java)
                    achievedGoalsList.add(result!!)
                }

                Resource.Success(achievedGoalsList)
            }
        }
    }
}