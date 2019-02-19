export interface IProjeTuru {
    id?: number;
    ad?: string;
    aciklama?: string;
    kisaltma?: string;
}

export class ProjeTuru implements IProjeTuru {
    constructor(public id?: number, public ad?: string, public aciklama?: string, public kisaltma?: string) {}
}
