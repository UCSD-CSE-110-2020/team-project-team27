package com.example.wwr;

import java.util.ArrayList;

public interface FirebaseObserver {
    void updateTeamList(ArrayList<String> teammateNames, ArrayList<String> teammatesEmails,
                        ArrayList<String> teammateColors, ArrayList<Boolean> pending);
    void updateInviteList(ArrayList<String> teammatesNames, ArrayList<String> teammatesEmails,
                                 ArrayList<String> teammateColors, ArrayList<Boolean> pending);
    void updateTeamRoute(ArrayList<Route> teammateRoutes);
    void updateProposedRouteList(ArrayList<ProposedRoute> proposedRouteArrayList);
    void inviteSuccessful(boolean isSuccessful);
}
