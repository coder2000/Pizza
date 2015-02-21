package ca.dieterlunn.android.pizza;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Patterns;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.regex.Matcher;

public class AccountUtils {
    public static class UserProfile {
        private String _primaryEmail;
        private String _primaryName;
        private String _primaryPhoneNumber;

        private List<String> _possibleEmails = Lists.newArrayList();
        private List<String> _possibleNames = Lists.newArrayList();
        private List<String> _possiblePhoneNumbers = Lists.newArrayList();

        private Uri _possiblePhoto;

        public String get_primaryEmail() {
            return _primaryEmail;
        }

        public String get_primaryPhoneNumber() {
            return _primaryPhoneNumber;
        }

        public String get_primaryName() {
            return _primaryName;
        }

        public Uri get_possiblePhoto(){
            return _possiblePhoto;
        }

        public List<String> get_possibleEmails() {
            return _possibleEmails;
        }

        public List<String> get_possibleNames() {
            return _possibleNames;
        }

        public List<String> get_possiblePhoneNumbers() {
            return _possiblePhoneNumbers;
        }

        public void addPossibleEmail(String email, boolean is_primary) {
            if (email == null) return;

            if (is_primary) {
                _primaryEmail = email;
            }

            _possibleEmails.add(email);
        }

        public void addPossibleEmail(String email) {
            addPossibleEmail(email, false);
        }

        public void addPossiblePhoneNumber(String number, boolean is_primary) {
            if (number == null) return;

            if (is_primary) {
                _primaryPhoneNumber = number;
            }

            _possiblePhoneNumbers.add(number);
        }

        public void addPossiblePhoneNumber(String number) {
            addPossiblePhoneNumber(number, false);
        }

        public void addPossibleName(String name) {
            if (name != null) {
                _possibleNames.add(name);
            }
        }

        public void addPossiblePhoto(Uri photo) {
            if (photo != null) {
                _possiblePhoto = photo;
            }
        }
    }

    public static UserProfile getUserProfile(Context context) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH ?
                getUserProfileICS(context) :
                getUserProfileGingerbread(context);
    }

    private static UserProfile getUserProfileGingerbread(Context context) {
        final Matcher valid_email_address = Patterns.EMAIL_ADDRESS.matcher("");
        final Account[] accounts = AccountManager.get(context).getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
        final UserProfile userProfile = new UserProfile();

        for (Account account : accounts) {
            if (valid_email_address.reset(account.name).matches()) {
                userProfile.addPossibleEmail(account.name);
            }
        }

        return userProfile;
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private static UserProfile getUserProfileICS(Context context) {
        final ContentResolver content = context.getContentResolver();
        final Cursor cursor = content.query(Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI, ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION, ContactsContract.Contacts.Data.MIMETYPE + "=? OR " + ContactsContract.Contacts.Data.MIMETYPE + "=? OR " + ContactsContract.Contacts.Data.MIMETYPE + "=? OR " + ContactsContract.Contacts.Data.MIMETYPE + "=?", new String[] {ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE}, ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");

        final UserProfile userProfile = new UserProfile();
        String mimeType;

        while (cursor.moveToNext()) {
            mimeType = cursor.getString(ProfileQuery.MIME_TYPE);

            switch (mimeType) {
                case ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE:
                    userProfile.addPossibleEmail(cursor.getString(ProfileQuery.EMAIL), cursor.getInt(ProfileQuery.IS_PRIMARY_EMAIL) > 0);
                case ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE:
                    userProfile.addPossibleName(cursor.getString(ProfileQuery.FAMILY_NAME) + " " + cursor.getString(ProfileQuery.GIVEN_NAME));
                case ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE:
                    userProfile.addPossiblePhoneNumber(cursor.getString(ProfileQuery.PHONE_NUMBER), cursor.getInt(ProfileQuery.IS_PRIMARY_PHONE_NUMBER) > 0);
                case ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE:
                    userProfile.addPossiblePhoto(Uri.parse(cursor.getString(ProfileQuery.PHOTO)));
            }
        }

        cursor.close();

        return userProfile;
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
                ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME,
                ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.IS_PRIMARY,
                ContactsContract.CommonDataKinds.Photo.PHOTO_URI,
                ContactsContract.Contacts.Data.MIMETYPE
        };

        int EMAIL = 0;
        int IS_PRIMARY_EMAIL = 1;
        int FAMILY_NAME = 2;
        int GIVEN_NAME = 3;
        int PHONE_NUMBER = 4;
        int IS_PRIMARY_PHONE_NUMBER = 5;
        int PHOTO = 6;
        int MIME_TYPE = 7;
    }
}
