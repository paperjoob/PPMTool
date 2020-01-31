import {GET_BACKLOG, GET_PROJECT_TASK, DELETE_PROJECT_TASK} from '../actions/Types';

// an initial state
const initialState = {
    project_tasks: [], // array of project tasks
    project_task: {} // individual project task
};

export default function(state = initialState, action) {
    switch(action.type) {
        case GET_BACKLOG:
            return {
                ...state,
                project_tasks: action.payload // return ARRAY OF PROJECT TASKS
            };
        case GET_PROJECT_TASK:
            return {
                ...state,
                project_task: action.payload // return a SINGLE PROJECT TASK
            };
            case DELETE_PROJECT_TASK:
                return {
                  ...state,
                  project_tasks: state.project_tasks.filter(
                    project_task => project_task.projectSequence !== action.payload
                  )
                };
        default:
            return state;
    }
};

