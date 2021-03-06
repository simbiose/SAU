/**
 * Copyright 2013-2014
 * Ademar Alves de Oliveira <ademar111190@gmail.com /> Simbio.se
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package simbio.se.sau.device;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Patterns;

import java.util.regex.Pattern;

import simbio.se.sau.API;

/**
 * This class provides device informations
 *
 * @author Ademar Alves de Oliveira <ademar111190@gmail.com>
 * @date 2013-sep-29 23:46:33
 * @since {@link API#Version_2_0_0}
 */
public class DeviceInformationsManager {

    private Context context;

    /**
     * @param context the {@link Context} to access device informations
     * @since {@link API#Version_2_0_0}
     */
    public DeviceInformationsManager(Context context) {
        this.context = context;
    }

    /**
     * Get the user primary email, need uses permission "android.permission.GET_ACCOUNTS"
     *
     * @param def the {@link String} default to return if get no e-mails
     * @return The primary user email or def, note it will returns null if android version is small
     * then 8 "froyo"
     * @since {@link API#Version_2_0_0}
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    public String getPrimaryUserEmail(String def) {
        if (android.os.Build.VERSION.SDK_INT >= 8) {
            Pattern emailPattern = Patterns.EMAIL_ADDRESS;
            AccountManager accountManager = AccountManager.get(context);
            if (accountManager == null)
                return null;
            Account[] accounts = accountManager.getAccounts();
            for (Account account : accounts)
                if (emailPattern.matcher(account.name).matches())
                    return account.name;
            return def;
        } else {
            return null;
        }
    }

    /**
     * Get the user primary email, need uses permission "android.permission.GET_ACCOUNTS"
     *
     * @return The primary user email or null, note it will returns null if android version is small
     * then 8 "froyo"
     * @since {@link API#Version_2_0_0}
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    public String getPrimaryUserEmailOrNull() {
        return getPrimaryUserEmail(null);
    }

}
