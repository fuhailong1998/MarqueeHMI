// IDragonBoardService.aidl
package com.android.dragonboard.service;

// Declare any non-default types here with import statements

import com.android.dragonboard.service.IDragonBoardListener;
interface IDragonBoardService {
   oneway void setStatus(int index, boolean status);
   oneway void setFrequency(int index, int frequency);
   oneway void addListener(IDragonBoardListener listener);
}
