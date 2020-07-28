package com.emerson.bankapp

import android.os.Parcel
import android.os.Parcelable


open class StatementModel : Parcelable {
    var title: String? = null
    var desc: String? = null
    var date: String? = null
    var value: Double? = null

    constructor()

    protected constructor(parcel: Parcel) {
        title = parcel.readString()
        desc = parcel.readString()
        date = parcel.readString()
        value = parcel.readDouble()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(title)
        dest.writeString(desc)
        dest.writeString(date)
        dest.writeDouble(value!!)
    }

    companion object CREATOR : Parcelable.Creator<StatementModel> {
        override fun createFromParcel(parcel: Parcel): StatementModel {
            return StatementModel(parcel)
        }

        override fun newArray(size: Int): Array<StatementModel?> {
            return arrayOfNulls(size)
        }
    }
}
