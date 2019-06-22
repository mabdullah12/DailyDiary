package com.abdullah.dailydiary.RoomDatabase

import android.app.NotificationManager
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import android.support.annotation.WorkerThread
import android.support.v4.util.Pair
import com.abdullah.dailydiary.helpers.CallBacks
import com.abdullah.dailydiary.helpers.DiaryEnums
import java.text.SimpleDateFormat
import java.util.*

class DiaryRepo (private val mDiaryDao: DiaryDao) {

    val diaryLiveList:LiveData<List<DiaryEntity>> = mDiaryDao.allDiaryEntity

    fun diaryLiveListByDate(dateString: String, callback: CallBacks) /*: List<DiaryEntity> */{
        diaryListByDate(mDiaryDao, callback).execute(dateString)
        //return mDiaryDao.getAllDiaryEntityByDate(dateString)
    }

    @WorkerThread
    fun insert(diaryEntity: DiaryEntity) {
        mDiaryDao.insert(diaryEntity)

    }

    @WorkerThread
    fun update(diaryEntity: DiaryEntity) {
        mDiaryDao.update(diaryEntity)

    }

    fun insert(id:Int, value:String, diaryEnums: DiaryEnums){
        insertAsynTask(mDiaryDao, id, value, diaryEnums).execute();
    }

    class insertAsynTask(private val mDiaryDao: DiaryDao, private val id: Int, private val value: String, private val diaryEnums: DiaryEnums) : AsyncTask<Void, Void, Void>() {
        lateinit var diaryEntity: DiaryEntity
        override fun doInBackground(vararg params: Void?): Void? {

            if (id != -1){
                diaryEntity = mDiaryDao.getDiaryEntityById(id)
                settingDiaryEntityWithEnum(diaryEntity, diaryEnums, value)
                mDiaryDao.update(diaryEntity)
            }
            else {
                diaryEntity = DiaryEntity()
                diaryEntity.date =  SimpleDateFormat("dd-MMM-yyyy").format(Calendar.getInstance().time).toString()
                settingDiaryEntityWithEnum(diaryEntity, diaryEnums, value)

                mDiaryDao.insert(diaryEntity)
            }



            return null
        }


        fun settingDiaryEntityWithEnum(diaryEntity: DiaryEntity , diaryEnums: DiaryEnums, value: String){
            if (diaryEnums == DiaryEnums.ONE ||  diaryEnums == DiaryEnums.FIVE){
                diaryEntity.goodDeed = value
            }
            else if (diaryEnums == DiaryEnums.TWO){
                diaryEntity.line1 = value
            }
            else if (diaryEnums == DiaryEnums.THREE){
                diaryEntity.line2 = value
            }
            else if (diaryEnums == DiaryEnums.FOUR){
                diaryEntity.line3 = value
            }
        }

    }

    class diaryListByDate(private val mDiaryDao: DiaryDao, private val callback: CallBacks): AsyncTask<String, Void, List<DiaryEntity>>() {
        override fun doInBackground(vararg params: String?): List<DiaryEntity>? {
            return mDiaryDao.getAllDiaryEntityByDate(params[0])
        }

        override fun onPostExecute(result: List<DiaryEntity>?) {
            super.onPostExecute(result)
            if (result != null && result.size > 0){
                callback.callBack(exploreDiaryEntity(result.get(0) ,  result.get(0).id))
            }
            else {
                callback.callBack(Pair(DiaryEnums.FIVE, -1))
            }
        }


        fun exploreDiaryEntity (diaryEntity: DiaryEntity, id: Int) : Pair<DiaryEnums, Int>{
            var retVal:Pair<DiaryEnums, Int> = Pair(DiaryEnums.ZERO, id)
            if (diaryEntity.goodDeed.isNullOrEmpty()){
                retVal = Pair(DiaryEnums.ONE, id)
            }
            else if (diaryEntity.line1.isNullOrEmpty()){
                retVal = Pair(DiaryEnums.TWO, id)
            }
            else if (diaryEntity.line2.isNullOrEmpty()){
                retVal = Pair(DiaryEnums.THREE, id)
            }
            else if (diaryEntity.line3.isNullOrEmpty()){
                retVal = Pair(DiaryEnums.FOUR, id)
            }


            return retVal
        }

    }


}