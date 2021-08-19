// IDragonBoardListener.aidl
package com.android.dragonboard.service;

// Declare any non-default types here with import statements

interface IDragonBoardListener {
    void onChange(int index, int status);
}
