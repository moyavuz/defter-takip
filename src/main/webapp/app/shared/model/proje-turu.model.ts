import { IProje } from 'app/shared/model/proje.model';

export interface IProjeTuru {
    id?: number;
    ad?: string;
    aciklama?: string;
    kisaltma?: string;
    proje?: IProje;
}

export class ProjeTuru implements IProjeTuru {
    constructor(public id?: number, public ad?: string, public aciklama?: string, public kisaltma?: string, public proje?: IProje) {}
}
