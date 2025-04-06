package com.seahorse.utils;

import com.seahorse.model.UpdateData;

public interface  UpdateComponent {
    public static void AddUpdate(UpdateComponent THIS){
        UpdateData.updateComponents.add(THIS);
    }
    public void Update();
}
