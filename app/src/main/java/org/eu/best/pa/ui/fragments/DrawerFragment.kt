package org.eu.best.pa.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.google.android.material.button.MaterialButton
import org.eu.best.pa.Config
import org.eu.best.pa.R
import org.eu.best.pa.ui.LoginActivity
import org.eu.best.pa.ui.MainActivity

class DrawerFragment : Fragment() {

    companion object {
        private const val LOGOUT_URL = "${Config.PA_URL}${Config.PA_LOGOUT}"
    }

    private var actionParser: WebView? = null
    private var btnLogout: MaterialButton? = null
    private var drawerOrganization: CardView? = null
    private var organizationViewNonexpanded: ConstraintLayout? = null
    private var organizationViewExpanded: ConstraintLayout? = null
    private var organizationArrow: LinearLayout? = null
    private var drawerEvents: CardView? = null
    private var eventsViewNonexpanded: ConstraintLayout? = null
    private var eventsViewExpanded: ConstraintLayout? = null
    private var eventsArrow: LinearLayout? = null
    private var drawerTools: CardView? = null
    private var toolsViewNonexpanded: ConstraintLayout? = null
    private var toolsViewExpanded: ConstraintLayout? = null
    private var toolsArrow: LinearLayout? = null
    private var drawerKnowledge: CardView? = null
    private var knowledgeViewNonexpanded: ConstraintLayout? = null
    private var knowledgeViewExpanded: ConstraintLayout? = null
    private var knowledgeArrow: LinearLayout? = null
    private var drawerCommunication: CardView? = null
    private var communicationViewNonexpanded: ConstraintLayout? = null
    private var communicationViewExpanded: ConstraintLayout? = null
    private var communicationArrow: LinearLayout? = null
    private var cardOther: CardView? = null
    private var btnLbgs: Button? = null
    private var btnRegions: Button? = null
    private var btnDepartments: Button? = null
    private var btnInternationProjects: Button? = null
    private var btnPositionTracker: Button? = null
    private var btnExternalEvents: Button? = null
    private var btnInternalEvents: Button? = null
    private var btnLocalEvents: Button? = null
    private var btnMarketingMaterials: Button? = null
    private var btnTraining: Button? = null
    private var btnTaskManager: Button? = null
    private var btnWiki: Button? = null
    private var btnDocuments: Button? = null
    private var btnLbgMaterials: Button? = null
    private var btnKm: Button? = null
    private var btnCompetitionTasks: Button? = null
    private var btnGames: Button? = null
    private var btnStatistics: Button? = null
    private var btnMailingLists: Button? = null
    private var btnVa: Button? = null
    private var btnBoardDecisions: Button? = null
    private var btnNews: Button? = null
    private var btnSurveys: Button? = null
    private var btnHelpdesk: Button? = null
    private var logoutState: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_drawer, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actionParser = view.findViewById(R.id.action_parser)
        btnLogout = view.findViewById(R.id.btn_logout)
        drawerOrganization = view.findViewById(R.id.card_organization)
        organizationViewNonexpanded = view.findViewById(R.id.organization_view_non_expanded)
        organizationViewExpanded = view.findViewById(R.id.organization_view_expanded)
        organizationArrow = view.findViewById(R.id.organization_arrow)
        drawerEvents = view.findViewById(R.id.card_events)
        eventsViewNonexpanded = view.findViewById(R.id.events_view_non_expanded)
        eventsViewExpanded = view.findViewById(R.id.events_view_expanded)
        eventsArrow = view.findViewById(R.id.events_arrow)
        drawerTools = view.findViewById(R.id.card_tools)
        toolsViewNonexpanded = view.findViewById(R.id.tools_view_non_expanded)
        toolsViewExpanded = view.findViewById(R.id.tools_view_expanded)
        toolsArrow = view.findViewById(R.id.tools_arrow)
        drawerKnowledge = view.findViewById(R.id.card_knowledge)
        knowledgeViewNonexpanded = view.findViewById(R.id.knowledge_view_non_expanded)
        knowledgeViewExpanded = view.findViewById(R.id.knowledge_view_expanded)
        knowledgeArrow = view.findViewById(R.id.knowledge_arrow)
        drawerCommunication = view.findViewById(R.id.card_communication)
        communicationViewNonexpanded = view.findViewById(R.id.communication_view_non_expanded)
        communicationViewExpanded = view.findViewById(R.id.communication_view_expanded)
        communicationArrow = view.findViewById(R.id.communication_arrow)
        cardOther = view.findViewById(R.id.card_other)
        btnLbgs = view.findViewById(R.id.btn_lbgs)
        btnRegions = view.findViewById(R.id.btn_regions)
        btnDepartments = view.findViewById(R.id.btn_departments)
        btnInternationProjects = view.findViewById(R.id.btn_international_projects)
        btnPositionTracker = view.findViewById(R.id.btn_position_tracker)
        btnExternalEvents = view.findViewById(R.id.btn_external_events)
        btnInternalEvents = view.findViewById(R.id.btn_internal_events)
        btnLocalEvents = view.findViewById(R.id.btn_local_events)
        btnMarketingMaterials = view.findViewById(R.id.btn_marketing_materials)
        btnTraining = view.findViewById(R.id.btn_training)
        btnTaskManager = view.findViewById(R.id.btn_task_manager)
        btnWiki = view.findViewById(R.id.btn_wiki)
        btnDocuments = view.findViewById(R.id.btn_documents)
        btnLbgMaterials = view.findViewById(R.id.btn_lbg_materials)
        btnKm = view.findViewById(R.id.btn_km)
        btnCompetitionTasks = view.findViewById(R.id.btn_competition_tasks)
        btnGames = view.findViewById(R.id.btn_games)
        btnStatistics = view.findViewById(R.id.btn_statistic)
        btnMailingLists = view.findViewById(R.id.btn_mailing_lists)
        btnVa = view.findViewById(R.id.btn_va)
        btnBoardDecisions = view.findViewById(R.id.btn_board_decisions)
        btnNews = view.findViewById(R.id.btn_news)
        btnSurveys = view.findViewById(R.id.btn_surveys)
        btnHelpdesk = view.findViewById(R.id.btn_helpdesk)

        drawerOrganization?.setBackgroundColor(0x00000000)
        drawerEvents?.setBackgroundColor(0x00000000)
        drawerTools?.setBackgroundColor(0x00000000)
        drawerKnowledge?.setBackgroundColor(0x00000000)
        drawerCommunication?.setBackgroundColor(0x00000000)
        cardOther?.setBackgroundColor(0x00000000)

        actionParser?.settings?.javaScriptEnabled = true
        actionParser?.settings?.domStorageEnabled = true
        actionParser?.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                val cookieManager: CookieManager = CookieManager.getInstance()
                cookieManager.setAcceptCookie(true)
                CookieManager.getInstance().flush()

                if (logoutState) {
                    startActivity(Intent(requireActivity(), LoginActivity::class.java))
                    requireActivity().finish()
                } else {
                    actionParser?.loadUrl("javascript:window.HTMLOUT.processHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');")
                }
            }
        }

        btnLogout?.setOnClickListener {
            logoutState = true
            actionParser?.loadUrl(LOGOUT_URL)
        }

        initDrawer()
        initializeDrawerButtons()
    }

    private fun initializeDrawerButtons() {
        btnLbgs?.setOnClickListener {
            closeDrawer()
        }

        btnRegions?.setOnClickListener {
            closeDrawer()
        }

        btnDepartments?.setOnClickListener {
            closeDrawer()
        }

        btnInternationProjects?.setOnClickListener {
            closeDrawer()
        }

        btnPositionTracker?.setOnClickListener {
            closeDrawer()
        }

        btnExternalEvents?.setOnClickListener {
            closeDrawer()
        }

        btnInternalEvents?.setOnClickListener {
            closeDrawer()
        }

        btnLocalEvents?.setOnClickListener {
            closeDrawer()
        }

        btnMarketingMaterials?.setOnClickListener {
            closeDrawer()
        }

        btnTraining?.setOnClickListener {
            closeDrawer()
        }

        btnTaskManager?.setOnClickListener {
            closeDrawer()
        }

        btnWiki?.setOnClickListener {
            closeDrawer()
        }

        btnDocuments?.setOnClickListener {
            closeDrawer()
        }

        btnLbgMaterials?.setOnClickListener {
            closeDrawer()
        }

        btnKm?.setOnClickListener {
            closeDrawer()
        }

        btnCompetitionTasks?.setOnClickListener {
            closeDrawer()
        }

        btnGames?.setOnClickListener {
            closeDrawer()
        }

        btnStatistics?.setOnClickListener {
            closeDrawer()
        }

        btnMailingLists?.setOnClickListener {
            closeDrawer()
        }

        btnVa?.setOnClickListener {
            closeDrawer()
        }

        btnBoardDecisions?.setOnClickListener {
            closeDrawer()
        }

        btnNews?.setOnClickListener {
            closeDrawer()
        }

        btnSurveys?.setOnClickListener {
            closeDrawer()
        }

        btnHelpdesk?.setOnClickListener {
            closeDrawer()
        }
    }

    private fun initDrawer() {
        organizationViewNonexpanded?.setOnClickListener {
            if (organizationViewExpanded?.visibility == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(drawerOrganization!!, AutoTransition())
                organizationViewExpanded?.visibility = View.GONE
                organizationArrow?.setBackgroundResource(R.drawable.ic_closed_arrow)
            } else {
                TransitionManager.beginDelayedTransition(drawerOrganization!!, AutoTransition())
                organizationViewExpanded?.visibility = View.VISIBLE
                organizationArrow?.setBackgroundResource(R.drawable.ic_opened_arrow)
            }

            TransitionManager.beginDelayedTransition(drawerEvents!!, AutoTransition())
            eventsViewExpanded?.visibility = View.GONE
            eventsArrow?.setBackgroundResource(R.drawable.ic_closed_arrow)

            TransitionManager.beginDelayedTransition(drawerTools!!, AutoTransition())
            toolsViewExpanded?.visibility = View.GONE
            toolsArrow?.setBackgroundResource(R.drawable.ic_closed_arrow)

            TransitionManager.beginDelayedTransition(drawerKnowledge!!, AutoTransition())
            knowledgeViewExpanded?.visibility = View.GONE
            knowledgeArrow?.setBackgroundResource(R.drawable.ic_closed_arrow)

            TransitionManager.beginDelayedTransition(drawerCommunication!!, AutoTransition())
            communicationViewExpanded?.visibility = View.GONE
            communicationArrow?.setBackgroundResource(R.drawable.ic_closed_arrow)

            TransitionManager.beginDelayedTransition(cardOther!!, AutoTransition())
        }

        eventsViewNonexpanded?.setOnClickListener {
            if (eventsViewExpanded?.visibility == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(drawerEvents!!, AutoTransition())
                eventsViewExpanded?.visibility = View.GONE
                eventsArrow?.setBackgroundResource(R.drawable.ic_closed_arrow)
            } else {
                TransitionManager.beginDelayedTransition(drawerEvents!!, AutoTransition())
                eventsViewExpanded?.visibility = View.VISIBLE
                eventsArrow?.setBackgroundResource(R.drawable.ic_opened_arrow)
            }

            TransitionManager.beginDelayedTransition(drawerOrganization!!, AutoTransition())
            organizationViewExpanded?.visibility = View.GONE
            organizationArrow?.setBackgroundResource(R.drawable.ic_closed_arrow)

            TransitionManager.beginDelayedTransition(drawerTools!!, AutoTransition())
            toolsViewExpanded?.visibility = View.GONE
            toolsArrow?.setBackgroundResource(R.drawable.ic_closed_arrow)

            TransitionManager.beginDelayedTransition(drawerKnowledge!!, AutoTransition())
            knowledgeViewExpanded?.visibility = View.GONE
            knowledgeArrow?.setBackgroundResource(R.drawable.ic_closed_arrow)

            TransitionManager.beginDelayedTransition(drawerCommunication!!, AutoTransition())
            communicationViewExpanded?.visibility = View.GONE
            communicationArrow?.setBackgroundResource(R.drawable.ic_closed_arrow)

            TransitionManager.beginDelayedTransition(cardOther!!, AutoTransition())
        }

        toolsViewNonexpanded?.setOnClickListener {
            if (toolsViewExpanded?.visibility == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(drawerTools!!, AutoTransition())
                toolsViewExpanded?.visibility = View.GONE
                toolsArrow?.setBackgroundResource(R.drawable.ic_closed_arrow)
            } else {
                TransitionManager.beginDelayedTransition(drawerTools!!, AutoTransition())
                toolsViewExpanded?.visibility = View.VISIBLE
                toolsArrow?.setBackgroundResource(R.drawable.ic_opened_arrow)
            }

            TransitionManager.beginDelayedTransition(drawerEvents!!, AutoTransition())
            eventsViewExpanded?.visibility = View.GONE
            eventsArrow?.setBackgroundResource(R.drawable.ic_closed_arrow)

            TransitionManager.beginDelayedTransition(drawerOrganization!!, AutoTransition())
            organizationViewExpanded?.visibility = View.GONE
            organizationArrow?.setBackgroundResource(R.drawable.ic_closed_arrow)

            TransitionManager.beginDelayedTransition(drawerKnowledge!!, AutoTransition())
            knowledgeViewExpanded?.visibility = View.GONE
            knowledgeArrow?.setBackgroundResource(R.drawable.ic_closed_arrow)

            TransitionManager.beginDelayedTransition(drawerCommunication!!, AutoTransition())
            communicationViewExpanded?.visibility = View.GONE
            communicationArrow?.setBackgroundResource(R.drawable.ic_closed_arrow)

            TransitionManager.beginDelayedTransition(cardOther!!, AutoTransition())
        }

        knowledgeViewNonexpanded?.setOnClickListener {
            if (knowledgeViewExpanded?.visibility == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(drawerKnowledge!!, AutoTransition())
                knowledgeViewExpanded?.visibility = View.GONE
                knowledgeArrow?.setBackgroundResource(R.drawable.ic_closed_arrow)
            } else {
                TransitionManager.beginDelayedTransition(drawerKnowledge!!, AutoTransition())
                knowledgeViewExpanded?.visibility = View.VISIBLE
                knowledgeArrow?.setBackgroundResource(R.drawable.ic_opened_arrow)
            }

            TransitionManager.beginDelayedTransition(drawerEvents!!, AutoTransition())
            eventsViewExpanded?.visibility = View.GONE
            eventsArrow?.setBackgroundResource(R.drawable.ic_closed_arrow)

            TransitionManager.beginDelayedTransition(drawerOrganization!!, AutoTransition())
            organizationViewExpanded?.visibility = View.GONE
            organizationArrow?.setBackgroundResource(R.drawable.ic_closed_arrow)

            TransitionManager.beginDelayedTransition(drawerTools!!, AutoTransition())
            toolsViewExpanded?.visibility = View.GONE
            toolsArrow?.setBackgroundResource(R.drawable.ic_closed_arrow)

            TransitionManager.beginDelayedTransition(drawerCommunication!!, AutoTransition())
            communicationViewExpanded?.visibility = View.GONE
            communicationArrow?.setBackgroundResource(R.drawable.ic_closed_arrow)

            TransitionManager.beginDelayedTransition(cardOther!!, AutoTransition())
        }

        communicationViewNonexpanded?.setOnClickListener {
            if (communicationViewExpanded?.visibility == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(drawerCommunication!!, AutoTransition())
                communicationViewExpanded?.visibility = View.GONE
                communicationArrow?.setBackgroundResource(R.drawable.ic_closed_arrow)
            } else {
                TransitionManager.beginDelayedTransition(drawerCommunication!!, AutoTransition())
                communicationViewExpanded?.visibility = View.VISIBLE
                communicationArrow?.setBackgroundResource(R.drawable.ic_opened_arrow)
            }

            TransitionManager.beginDelayedTransition(drawerEvents!!, AutoTransition())
            eventsViewExpanded?.visibility = View.GONE
            eventsArrow?.setBackgroundResource(R.drawable.ic_closed_arrow)

            TransitionManager.beginDelayedTransition(drawerOrganization!!, AutoTransition())
            organizationViewExpanded?.visibility = View.GONE
            organizationArrow?.setBackgroundResource(R.drawable.ic_closed_arrow)

            TransitionManager.beginDelayedTransition(drawerTools!!, AutoTransition())
            toolsViewExpanded?.visibility = View.GONE
            toolsArrow?.setBackgroundResource(R.drawable.ic_closed_arrow)

            TransitionManager.beginDelayedTransition(drawerKnowledge!!, AutoTransition())
            knowledgeViewExpanded?.visibility = View.GONE
            knowledgeArrow?.setBackgroundResource(R.drawable.ic_closed_arrow)

            TransitionManager.beginDelayedTransition(cardOther!!, AutoTransition())
        }
    }

    private fun closeDrawer() {
        TransitionManager.beginDelayedTransition(drawerOrganization!!, AutoTransition())
        organizationViewExpanded?.visibility = View.GONE
        organizationArrow?.setBackgroundResource(R.drawable.ic_closed_arrow)

        TransitionManager.beginDelayedTransition(drawerEvents!!, AutoTransition())
        eventsViewExpanded?.visibility = View.GONE
        eventsArrow?.setBackgroundResource(R.drawable.ic_closed_arrow)

        TransitionManager.beginDelayedTransition(drawerTools!!, AutoTransition())
        toolsViewExpanded?.visibility = View.GONE
        toolsArrow?.setBackgroundResource(R.drawable.ic_closed_arrow)

        TransitionManager.beginDelayedTransition(drawerKnowledge!!, AutoTransition())
        knowledgeViewExpanded?.visibility = View.GONE
        knowledgeArrow?.setBackgroundResource(R.drawable.ic_closed_arrow)

        TransitionManager.beginDelayedTransition(drawerCommunication!!, AutoTransition())
        communicationViewExpanded?.visibility = View.GONE
        communicationArrow?.setBackgroundResource(R.drawable.ic_closed_arrow)

        val parentActivity = requireActivity()

        if (parentActivity is MainActivity) {
            parentActivity.closeDrawer()
        }
    }
}