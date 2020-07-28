package com.emerson.bankapp

import android.os.Parcel
import android.os.Parcelable


class StatementViewModel : StatementModel, Parcelable {
    constructor()
    protected constructor(parcel: Parcel) : super(parcel) {
        title = parcel.readString()
        desc = parcel.readString()
        date = parcel.readString()
        value = parcel.readDouble()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        super.writeToParcel(dest, flags)
        dest.writeString(title)
        dest.writeString(desc)
        dest.writeString(date)
        dest.writeDouble(value!!)
    }

    companion object CREATOR : Parcelable.Creator<StatementViewModel> {
        override fun createFromParcel(parcel: Parcel): StatementViewModel {
            return StatementViewModel(parcel)
        }

        override fun newArray(size: Int): Array<StatementViewModel?> {
            return arrayOfNulls(size)
        }
    }
}
